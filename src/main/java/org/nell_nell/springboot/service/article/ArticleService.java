package org.nell_nell.springboot.service.article;

import lombok.RequiredArgsConstructor;
import org.nell_nell.springboot.domain.article.Article;
import org.nell_nell.springboot.domain.article.ArticleRepository;
import org.nell_nell.springboot.web.dto.PostsSaveRequestDto;
import org.nell_nell.springboot.web.dto.article_dto.ArticleListResponseDto;
import org.nell_nell.springboot.web.dto.article_dto.ArticleResponseDto;
import org.nell_nell.springboot.web.dto.article_dto.ArticleSaveRequestDto;
import org.nell_nell.springboot.web.dto.article_dto.ArticleUpdateRequestDto;
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

    @Transactional(readOnly = true)
    public List<ArticleListResponseDto> findAllDesc() {
        return articleRepository.findHumorBoard().stream()
                .map(ArticleListResponseDto::new)
                .collect(Collectors.toList());
    }

}
