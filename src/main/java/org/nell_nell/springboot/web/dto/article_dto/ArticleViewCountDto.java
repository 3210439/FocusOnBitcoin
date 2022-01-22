package org.nell_nell.springboot.web.dto.article_dto;

import lombok.Builder;

public class ArticleViewCountDto {
    private Long view_count;

    @Builder
    public ArticleViewCountDto(Long view_count){
        this.view_count = view_count;
    }
}
