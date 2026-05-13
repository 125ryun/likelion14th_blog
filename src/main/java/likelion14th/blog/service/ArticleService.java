package likelion14th.blog.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import likelion14th.blog.domain.Article;
import likelion14th.blog.dto.response.ArticleDetailResponse;
import likelion14th.blog.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional
    public ArticleDetailResponse addArticle(
            String title, String content, String author, String password
    ) {
        Article article = new Article(title, content, author, password);
        articleRepository.save(article);
        return ArticleDetailResponse.from(article);
    }

    @Transactional()
    public ArticleDetailResponse getOneArticle(Long id) {
        Article article =
                articleRepository
                        .findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시물을 찾을 수 없습니다."));
        return ArticleDetailResponse.from(article);
    }

    @Transactional()
    public ArticleDetailResponse updateArticle(Long id, String title, String content) {
        Article article =
                articleRepository
                        .findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시물을 찾을 수 없습니다."));
        article.update(title, content);
        articleRepository.save(article);
        return ArticleDetailResponse.from(article);
    }

    @Transactional()
    public Void deleteArticle(Long id) {
        articleRepository.deleteById(id);
        return null;
    }

}
