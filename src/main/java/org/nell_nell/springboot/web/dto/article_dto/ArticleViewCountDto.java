package org.nell_nell.springboot.web.dto.article_dto;

import lombok.Builder;

public class ArticleViewCountDto {
    private Long viewCount;

    @Builder
    public ArticleViewCountDto(Long viewCount){
        this.viewCount = viewCount;
    }
}
