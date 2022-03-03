package org.nell_nell.springboot.web;

import lombok.RequiredArgsConstructor;
import org.nell_nell.springboot.config.auth.LoginUser;
import org.nell_nell.springboot.config.auth.dto.SessionUser;
import org.nell_nell.springboot.domain.user.User;
import org.nell_nell.springboot.service.article.ArticleService;
import org.nell_nell.springboot.service.posts.PostsService;
import org.nell_nell.springboot.web.dto.PostsResponseDto;
import org.nell_nell.springboot.web.dto.PostsSaveRequestDto;
import org.nell_nell.springboot.web.dto.PostsUpdateRequestDto;
import org.nell_nell.springboot.web.dto.article_dto.ArticleResponseDto;
import org.nell_nell.springboot.web.dto.article_dto.ArticleSaveRequestDto;
import org.nell_nell.springboot.web.dto.article_dto.ArticleUpdateRequestDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ArticleApiController {

    private final ArticleService articleService;

    @PostMapping("/api/v1/article")
    public Long save(@RequestBody ArticleSaveRequestDto requestDto, @LoginUser SessionUser user, @AuthenticationPrincipal User user_s) {
        if (user != null)
            requestDto.setUserId(user.getName());
        else if (user_s != null)
            requestDto.setUserId(user_s.getName());
        return articleService.save(requestDto);
    }

    @PutMapping("/api/v1/article/{id}")
    public Long update(@PathVariable Long id, @RequestBody ArticleUpdateRequestDto requestDto) {
        return articleService.update(id, requestDto);
    }

    @DeleteMapping("/api/v1/article/{id}")
    public Long delete(@PathVariable Long id) {
        articleService.delete(id);
        return id;
    }

    @GetMapping("/api/v1/article/{id}")
    public ArticleResponseDto findById(@PathVariable Long id) {
        return articleService.findById(id);
    }
}
