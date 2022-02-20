package org.nell_nell.springboot.config.auth.dto;

import lombok.Getter;
import org.nell_nell.springboot.domain.user.Role;
import org.nell_nell.springboot.domain.user.User;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;
    private Role role;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
        this.role = user.getRole();
    }
}