package likelion14th.blog.controller;

import likelion14th.blog.dto.request.CommentRequest;
import likelion14th.blog.dto.response.ApiResponse;
import likelion14th.blog.dto.response.CommentResponse;
import likelion14th.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{articleId}/comments")
    public ResponseEntity<ApiResponse<CommentResponse>> addComment(
            @PathVariable Long articleId,
            @RequestBody CommentRequest request
    ) {
        CommentResponse commentResponse =
                commentService.addComment(articleId, request.getContent(), request.getAuthor());
        return ResponseEntity.ok(
                ApiResponse.success(201, "댓글 생성에 성공했습니다.", commentResponse));
    }

    @GetMapping("/{articleId}/comments")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getComments(
            @PathVariable Long articleId
    ) {
        List<CommentResponse> commentResponses = commentService.getComments(articleId);
        return ResponseEntity.ok(
                ApiResponse.success(200, "게시글 전체 조회에 성공했습니다.", commentResponses));
    }

}
