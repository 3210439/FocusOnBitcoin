package org.nell_nell.springboot.web.dto.user_dto;

import lombok.*;
import org.nell_nell.springboot.domain.user.Role;
import org.nell_nell.springboot.domain.user.User;

import java.time.LocalDateTime;

@Getter
public class UserListResponseDto {

    // Email을 뜻한다.
    private String username;
    private String name;
    private long id;
    private String picture;
    private Role role;
    private LocalDateTime created_date;

    public UserListResponseDto(User entity){
        this.username = entity.getUsername();
        this.name = entity.getName();
        this.id = entity.getId();
        this.picture = entity.getPicture();
        this.role = entity.getRole();
        this.created_date = entity.getCreated_date();
    }
}
