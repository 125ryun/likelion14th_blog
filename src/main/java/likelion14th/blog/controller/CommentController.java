package likelion14th.blog.controller;

import likelion14th.blog.dto.request.CommentRequest;
import likelion14th.blog.dto.response.ApiResponse;
import likelion14th.blog.dto.response.CommentResponse;
import likelion14th.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
