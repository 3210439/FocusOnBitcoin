package org.nell_nell.springboot.web.dto;

import lombok.Getter;
import org.nell_nell.springboot.domain.posts.Posts;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {
    private Long id;
    private String title;
    private String author;
    private LocalDateTime modified_date;

    public PostsListResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.modified_date = entity.getModified_date();
    }
}