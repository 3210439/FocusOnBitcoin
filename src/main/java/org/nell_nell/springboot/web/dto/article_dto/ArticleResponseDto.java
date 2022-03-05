package org.nell_nell.springboot.web.dto.article_dto;

import org.nell_nell.springboot.domain.article.Article;
import org.nell_nell.springboot.domain.posts.Posts;

public class ArticleResponseDto {
    private Long id;
    private String title;
    private String content;
    private String userId;
    private long viewCount;

    public ArticleResponseDto(Article entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.userId = entity.getUserId();
        this.viewCount = entity.getViewCount();
    }
}
