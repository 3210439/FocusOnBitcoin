package org.nell_nell.springboot.service.article;

import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.nell_nell.springboot.domain.article.Article;
import org.nell_nell.springboot.domain.article.ArticleRepository;
import org.nell_nell.springboot.web.dto.PostsSaveRequestDto;
import org.nell_nell.springboot.web.dto.article_dto.ArticleListResponseDto;
import org.nell_nell.springboot.web.dto.article_dto.ArticleResponseDto;
import org.nell_nell.springboot.web.dto.article_dto.ArticleSaveRequestDto;
import org.nell_nell.springboot.web.dto.article_dto.ArticleUpdateRequestDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional
    public Long save(ArticleSaveRequestDto requestDto) {
        return articleRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, ArticleUpdateRequestDto requestDto){
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. "+id));

        article.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }
    
    @Transactional
    public void delete (Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."+id));

        articleRepository.delete(article);
    }

    @Transactional(readOnly = true)
    public ArticleResponseDto findById(Long id) {
        Article entity = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."+id));
        return new ArticleResponseDto(entity);
    }

    @Transactional
    public int updateViewCount(Long id) {
        return articleRepository.updateViewCount(id);
    }


    @Transactional(readOnly = true)
    public List<ArticleListResponseDto> findByCategory(String category, Pageable pageable) {
        return articleRepository.findByCategory(category, pageable).stream()
                .map(ArticleListResponseDto::new)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<ArticleListResponseDto> findByCategoryAndTitleContaining(String category, String title, Pageable pageable) {
        return articleRepository.findByCategoryAndTitleContaining(category, title, pageable).stream()
                .map(ArticleListResponseDto::new)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<ArticleListResponseDto> findByCategoryAndUserIdContaining(String category, String userId, Pageable pageable) {
        return articleRepository.findByCategoryAndUserIdContaining(category, userId, pageable).stream()
                .map(ArticleListResponseDto::new)
                .collect(Collectors.toList());
    }
}
