package org.nell_nell.springboot.web.dto.article_dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleUpdateRequestDto {
    private String title;
    private String content;

    @Builder
    public ArticleUpdateRequestDto(String title, String content){
        this.title = title;
        this.content = content;
    }
}
