package org.nell_nell.springboot.web.dto.user_dto;

import lombok.*;

/**
 * 유저 회원가입용 Dto
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRegisterDto {

    private String username;
    private String nickname;
    private String password;

    @Builder
    public UserRegisterDto(String username, String nickname, String password){
        this.username = username;
        this.nickname = nickname;
        this.password = password;
    }
}
