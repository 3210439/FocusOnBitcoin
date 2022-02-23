package org.nell_nell.springboot.web;

import lombok.RequiredArgsConstructor;
import org.nell_nell.springboot.config.auth.LoginUser;
import org.nell_nell.springboot.config.auth.dto.SessionUser;
import org.nell_nell.springboot.domain.user.User;
import org.nell_nell.springboot.service.article.ArticleService;
import org.nell_nell.springboot.service.posts.PostsService;
import org.nell_nell.springboot.web.dto.PostsResponseDto;
import org.nell_nell.springboot.web.dto.article_dto.ArticleResponseDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static org.nell_nell.springboot.common_features.ComFunc.checkUser;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final ArticleService articleService;
    //private final HttpSession httpSession;

    @GetMapping("/index")
    public String index(Model model, @LoginUser SessionUser user, @AuthenticationPrincipal User user_s) {
        // 가져온 결과를 posts로 전달한다.
        model.addAttribute("posts", postsService.findAllDesc());
        checkUser(model, user, user_s);
        return "index";
    }


    @GetMapping("/")
    public String main(Model model, @LoginUser SessionUser user, @AuthenticationPrincipal User user_s) {
        checkUser(model, user, user_s);
        return "main";
    }

    @GetMapping("/homeLogin")
    public String homeLogin() {
        return "homeLogin";
    }

    @GetMapping("/altBoard")
    public String altBoard(Model model, @LoginUser SessionUser user, @AuthenticationPrincipal User user_s)
    {
        model.addAttribute("article", articleService.findByCategory("alt"));
        model.addAttribute("category", "알트 코인");
        checkUser(model, user, user_s);

        return "article-select";
    }
    @GetMapping("/majorBoard")
    public String majorBoard(Model model, @LoginUser SessionUser user, @AuthenticationPrincipal User user_s)
    {
        model.addAttribute("article", articleService.findByCategory("major"));
        model.addAttribute("category", "메이저 코인");
        checkUser(model, user, user_s);

        return "article-select";
    }
    @GetMapping("/humorBoard")
    public String humorBoard(Model model, @LoginUser SessionUser user, @AuthenticationPrincipal User user_s)
    {
        // 가져온 결과를 posts로 전달한다.
        model.addAttribute("article", articleService.findByCategory("humor"));
        model.addAttribute("category", "유머");
        checkUser(model, user, user_s);

        return "article-select";
    }

    @GetMapping("/QnA")
    public String QnABoard(Model model, @LoginUser SessionUser user, @AuthenticationPrincipal User user_s)
    {
        model.addAttribute("article", articleService.findByCategory("QnA"));
        model.addAttribute("category", "Q&A");
        checkUser(model, user, user_s);

        return "article-select";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";

    }
    @GetMapping("/article/save")
    public String articleSave(Model model, @LoginUser SessionUser user, @AuthenticationPrincipal User user_s) {
        checkUser(model, user, user_s);

        return "article-save";

    }


    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }

    @GetMapping("/article/update/{id}")
    public String articleUpdate(@PathVariable Long id, Model model) {
        ArticleResponseDto dto = articleService.findById(id);
        model.addAttribute("article", dto);

        return "shown-part";
    }

}
