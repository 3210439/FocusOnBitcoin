package org.nell_nell.springboot.web.dto.article_dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nell_nell.springboot.domain.article.Article;

@Getter
@Setter
@NoArgsConstructor
public class ArticleSaveRequestDto {
    private String title;
    private String content;
    private String userId;
    private String ip_address;
    private String category;

    @Builder
    public ArticleSaveRequestDto(String title, String content, String userId, String ip_address, String category) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.ip_address = ip_address;
        this.category = category;
    }

    public Article toEntity() {
        return Article.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .category(category)
                .ip_address(ip_address)
                .build();
    }
}
