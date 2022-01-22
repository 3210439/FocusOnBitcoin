package org.nell_nell.springboot.web.dto.article_dto;

import lombok.Builder;

public class ArticleUpdateRequestDto {
    private String title;
    private String content;

    @Builder
    public ArticleUpdateRequestDto(String title, String content){
        this.title = title;
        this.content = content;
    }
}
