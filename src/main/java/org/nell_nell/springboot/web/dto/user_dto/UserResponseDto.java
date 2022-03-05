package org.nell_nell.springboot.web.dto.user_dto;

import org.nell_nell.springboot.domain.article.Article;
import org.nell_nell.springboot.domain.user.Role;
import org.nell_nell.springboot.domain.user.User;

public class UserResponseDto {
    private Long id;
    private String username;
    private String name;
    private String picture;
    private Role role;

    public UserResponseDto(User entity) {
        this.id = entity.getId();
        this.username = entity.getUsername();
        this.name = entity.getName();
        this.picture = entity.getPicture();
        this.role = entity.getRole();
    }
}
