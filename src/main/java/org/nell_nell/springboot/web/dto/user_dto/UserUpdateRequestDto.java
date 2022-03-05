package org.nell_nell.springboot.web.dto.user_dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.nell_nell.springboot.domain.user.Role;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String name;
    private String picture;
    private Role role;

    @Builder
    public UserUpdateRequestDto(String name, String picture, Role role){
        this.name = name;
        this.picture = picture;
        this.role = role;
    }
}
