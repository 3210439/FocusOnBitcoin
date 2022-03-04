package org.nell_nell.springboot.web;

import lombok.RequiredArgsConstructor;
import org.nell_nell.springboot.config.auth.LoginUser;
import org.nell_nell.springboot.config.auth.dto.SessionUser;
import org.nell_nell.springboot.domain.user.User;
import org.nell_nell.springboot.service.article.ArticleService;
import org.nell_nell.springboot.service.posts.PostsService;
import org.nell_nell.springboot.web.dto.PostsResponseDto;
import org.nell_nell.springboot.web.dto.article_dto.ArticleListResponseDto;
import org.nell_nell.springboot.web.dto.article_dto.ArticleResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

import static org.nell_nell.springboot.common_features.ComFunc.checkUser;
import static org.nell_nell.springboot.common_features.ComFunc.searchArticle;

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
        List<ArticleListResponseDto> lst = articleService.findTop4AllByOrderByViewCountDesc();
        model.addAttribute("article", lst);
        return "main";
    }

    @GetMapping(value={"/homeLogin", "/homeLogin/{search}"})
    public String homeLogin() {
        return "homeLogin";
    }

    @GetMapping(value={"/altBoard","/altBoard/{search}"})
    public String altBoard(Model model, @LoginUser SessionUser user, @AuthenticationPrincipal User user_s,
                           @PageableDefault(sort="id", direction=Sort.Direction.ASC) Pageable pageable,
                           @PathVariable(required = false) String search)
    {
        List<ArticleListResponseDto> lst;
        String category = "alt";
        lst = searchArticle(search, category, pageable, articleService);
        model.addAttribute("article", lst);
        model.addAttribute("category", "알트 코인");
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("url", "/altBoard");
        if(lst.isEmpty())
            model.addAttribute("notNext", "disabled");
        checkUser(model, user, user_s);

        return "article-select";
    }
    @GetMapping(value = {"/majorBoard/{search}","/majorBoard"})
    public String majorBoard(Model model, @LoginUser SessionUser user, @AuthenticationPrincipal User user_s,
                            @PageableDefault(sort="id", direction=Sort.Direction.ASC) Pageable pageable,
                             @PathVariable(required = false) String search)
    {
        List<ArticleListResponseDto> lst;
        String category = "major";
        lst = searchArticle(search, category, pageable, articleService);
        model.addAttribute("article", lst);
        model.addAttribute("category", "메이저 코인");
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("url", "/majorBoard");
        if(lst.isEmpty())
            model.addAttribute("notNext", "disabled");
        checkUser(model, user, user_s);

        return "article-select";
    }
    @GetMapping(value = {"/humorBoard", "/humorBoard/{search}"})
    public String humorBoard(Model model, @LoginUser SessionUser user, @AuthenticationPrincipal User user_s,
                             @PageableDefault(sort="id", direction=Sort.Direction.ASC) Pageable pageable,
                             @PathVariable(required = false) String search)
    {
        List<ArticleListResponseDto> lst;
        String category = "humor";
        lst = searchArticle(search, category, pageable, articleService);
        model.addAttribute("article", lst);
        model.addAttribute("category", "유머");
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("url", "/humorBoard");
        if(lst.isEmpty())
            model.addAttribute("notNext", "disabled");
        checkUser(model, user, user_s);

        return "article-select";
    }

    @GetMapping(value={"/QnA","/QnA/{search}"})
    public String QnABoard(Model model, @LoginUser SessionUser user, @AuthenticationPrincipal User user_s,
                           @PageableDefault(sort="id", direction=Sort.Direction.ASC) Pageable pageable,
                           @PathVariable(required = false) String search)
    {
        List<ArticleListResponseDto> lst;
        String category = "QnA";
        lst = searchArticle(search, category, pageable, articleService);
        model.addAttribute("article", lst);
        model.addAttribute("category", "Q&A");
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("url", "/QnA");
        if(lst.isEmpty())
            model.addAttribute("notNext", "disabled");
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

    @GetMapping("/article/select/{id}")
    public String articleSelect(@PathVariable Long id, Model model) {
        ArticleResponseDto dto = articleService.findById(id);
        articleService.updateViewCount(id);
        model.addAttribute("article", dto);

        return "shown-part";
    }

    @GetMapping("/article/update/{id}")
    public String articleUpdate(@PathVariable Long id, Model model) {
        ArticleResponseDto dto = articleService.findById(id);
        model.addAttribute("article", dto);

        return "article-update";
    }

}
