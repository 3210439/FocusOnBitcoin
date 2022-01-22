package org.nell_nell.springboot.web.dto.article_dto;

import org.nell_nell.springboot.domain.article.Article;

import java.time.LocalDateTime;

public class ArticleListResponseDto {
    private Long id;
    private String title;
    private String user_id;
    private Long view_count;
    private LocalDateTime modifiedDate;

    public ArticleListResponseDto(Article entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.user_id = entity.getUser_id();
        this.view_count = entity.getView_count();
        this.modifiedDate = entity.getModifiedDate();
    }
}
