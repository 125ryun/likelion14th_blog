package likelion14th.blog.service;

import jakarta.persistence.EntityNotFoundException;
import likelion14th.blog.domain.Article;
import likelion14th.blog.domain.Comment;
import likelion14th.blog.dto.response.CommentResponse;
import likelion14th.blog.exception.ArticleNotFoundException;
import likelion14th.blog.repository.ArticleRepository;
import likelion14th.blog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public CommentResponse addComment(
        Long articleId, String content, String author
    ) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException("해당 ID의 게시물을 찾을 수 없습니다."));
//                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시물을 찾을 수 없습니다."));

        Comment comment = new Comment(content, author, article);
        commentRepository.save(comment);

        return CommentResponse.of(article.getId(), comment);
    }

    public List<CommentResponse> getComments(Long articleId) {
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        return comments.stream()
                .map(comment -> CommentResponse.of(articleId, comment))
                .toList();
    }

}
