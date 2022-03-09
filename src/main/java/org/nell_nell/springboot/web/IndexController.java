package org.nell_nell.springboot.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.parser.JSONParser;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.nell_nell.springboot.common_features.HttpClient;
import org.nell_nell.springboot.config.auth.LoginUser;
import org.nell_nell.springboot.config.auth.dto.SessionUser;
import org.nell_nell.springboot.domain.user.User;
import org.nell_nell.springboot.service.article.ArticleService;
import org.nell_nell.springboot.service.posts.PostsService;
import org.nell_nell.springboot.service.user.UserService;
import org.nell_nell.springboot.web.dto.PostsResponseDto;
import org.nell_nell.springboot.web.dto.article_dto.ArticleListResponseDto;
import org.nell_nell.springboot.web.dto.article_dto.ArticleResponseDto;
import org.nell_nell.springboot.web.dto.coinListDto;
import org.nell_nell.springboot.web.dto.user_dto.UserListResponseDto;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.nell_nell.springboot.common_features.ComFunc.*;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final ArticleService articleService;
    private final UserService userService;
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
        String response;
        articleService.showAnnouncement(model);

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
        hasSearchCondition("/altBoard", search, model);
        String category = "alt";
        lst = searchArticle(search, category, pageable, articleService);
        model.addAttribute("article", lst);
        model.addAttribute("category", "알트 코인");
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
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
        hasSearchCondition("/majorBoard", search, model);
        String category = "major";
        lst = searchArticle(search, category, pageable, articleService);
        model.addAttribute("article", lst);
        model.addAttribute("category", "메이저 코인");
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
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
        hasSearchCondition("/humorBoard", search, model);
        String category = "humor";
        lst = searchArticle(search, category, pageable, articleService);
        model.addAttribute("article", lst);
        model.addAttribute("category", "유머");
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
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
        hasSearchCondition("/QnA", search, model);
        String category = "QnA";
        lst = searchArticle(search, category, pageable, articleService);
        model.addAttribute("article", lst);
        model.addAttribute("category", "Q&A");
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        if(lst.isEmpty())
            model.addAttribute("notNext", "disabled");
        checkUser(model, user, user_s);

        return "article-select";
    }
    @GetMapping(value={"/user","/user/{search}"})
    public String userList(Model model, @LoginUser SessionUser user, @AuthenticationPrincipal User user_s,
                           @PageableDefault(sort="id", direction=Sort.Direction.ASC) Pageable pageable,
                           @PathVariable(required = false) String search)
    {
        List<UserListResponseDto> lst = searchUser(search, pageable, userService);
        hasSearchCondition("/user",search, model);
        model.addAttribute("user", lst);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        if(lst.isEmpty())
            model.addAttribute("notNext", "disabled");
        checkUser(model, user, user_s);

        return "user-select";
    }
    @GetMapping("/user/update/{id}")
    public String userUpdate(Model model, @LoginUser SessionUser user, @AuthenticationPrincipal User user_s,
                             @PathVariable Long id){
        checkUser(model, user, user_s);
        model.addAttribute("user", userService.findById(id));
        return "user-update";
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
    public String articleUpdate(@PathVariable Long id, Model model, @LoginUser SessionUser user, @AuthenticationPrincipal User user_s) {
        ArticleResponseDto dto = articleService.findByIdAndCheckUser(id, user, user_s);
        model.addAttribute("article", dto);

        return "article-update";
    }

    @GetMapping("/chart")
    public String showChart(Model model, @LoginUser SessionUser user, @AuthenticationPrincipal User user_s) {

        return "chart";
    }

}
