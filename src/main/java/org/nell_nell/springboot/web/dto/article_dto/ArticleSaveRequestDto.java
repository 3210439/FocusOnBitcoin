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
    private String user_id;
    private String ip_address;
    private String category;

    @Builder
    public ArticleSaveRequestDto(String title, String content, String user_id, String ip_address, String category) {
        this.title = title;
        this.content = content;
        this.user_id = user_id;
        this.ip_address = ip_address;
        this.category = category;
    }

    public Article toEntity() {
        return Article.builder()
                .title(title)
                .content(content)
                .user_id(user_id)
                .category(category)
                .ip_address(ip_address)
                .build();
    }
}
