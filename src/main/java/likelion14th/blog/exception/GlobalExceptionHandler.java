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

    private ResponseEntity<ApiResponse<Void>> buildErrorResponse(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(ApiResponse.fail(status.value(), message));
    }

}
