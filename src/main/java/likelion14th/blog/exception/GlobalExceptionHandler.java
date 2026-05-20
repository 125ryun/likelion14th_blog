package likelion14th.blog.exception;

import jakarta.persistence.EntityNotFoundException;
import likelion14th.blog.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleEntityNotFoundException(
            EntityNotFoundException e
    ) {
        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                e.getMessage()
        );
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleArticleNotFoundException (
        ArticleNotFoundException e
    ) {
        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                e.getMessage()
        );
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handlePermissionDeniedException (
            PermissionDeniedException e
    ) {
        return buildErrorResponse(
                HttpStatus.FORBIDDEN,
                e.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ApiResponse<List<String>>> handleValidationExceptions(
            MethodArgumentNotValidException e
    ) {
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "입력값이 유효하지 않습니다.",
                getErrorFields(e)
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "서버 내부 오류가 발생했습니다."
        );
    }

    private static List<String> getErrorFields(MethodArgumentNotValidException e) {
        return e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());
    }

    private ResponseEntity<ApiResponse<Void>> buildErrorResponse(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(ApiResponse.fail(status.value(), message));
    }

    private <T> ResponseEntity<ApiResponse<T>> buildErrorResponse(HttpStatus status, String message, T data) {
        return ResponseEntity
                .status(status)
                .body(ApiResponse.fail(status.value(), message, data));
    }

}
