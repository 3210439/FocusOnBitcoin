package org.nell_nell.springboot.web;

import lombok.RequiredArgsConstructor;
import org.nell_nell.springboot.service.user.UserService;
import org.nell_nell.springboot.web.dto.article_dto.ArticleUpdateRequestDto;
import org.nell_nell.springboot.web.dto.user_dto.UserUpdateRequestDto;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserApiController {
    final private UserService userService;

    @PutMapping("/api/user/update/{id}")
    public Long update(@PathVariable Long id, @RequestBody UserUpdateRequestDto requestDto) {
        return userService.update(id, requestDto);
    }

    @DeleteMapping("/api/user/delete/{id}")
    public Long delete(@PathVariable Long id) {
        userService.delete(id);
        return id;
    }
}
