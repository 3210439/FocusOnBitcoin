package org.nell_nell.springboot.web.dto.article_dto;

import org.nell_nell.springboot.domain.article.Article;
import org.nell_nell.springboot.domain.posts.Posts;

public class ArticleResponseDto {
    private Long id;
    private String title;
    private String content;
    private String user_id;
    private long view_count;

    public ArticleResponseDto(Article entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.user_id = entity.getUser_id();
        this.view_count = entity.getView_count();
    }
}
