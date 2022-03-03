package org.nell_nell.springboot.web.dto.article_dto;

import lombok.Getter;
import org.nell_nell.springboot.domain.article.Article;

import java.time.LocalDateTime;

@Getter
public class ArticleListResponseDto {
    private Long id;
    private String title;
    private String userId;
    private Long view_count;
    private LocalDateTime modified_date;

    public ArticleListResponseDto(Article entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.userId = entity.getUserId();
        this.view_count = entity.getView_count();
        this.modified_date = entity.getModified_date();
    }
}
