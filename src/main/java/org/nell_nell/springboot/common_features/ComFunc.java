package org.nell_nell.springboot.common_features;

import org.nell_nell.springboot.config.auth.LoginUser;
import org.nell_nell.springboot.config.auth.dto.SessionUser;
import org.nell_nell.springboot.domain.user.User;
import org.nell_nell.springboot.service.article.ArticleService;
import org.nell_nell.springboot.service.user.UserService;
import org.nell_nell.springboot.web.dto.article_dto.ArticleListResponseDto;
import org.nell_nell.springboot.web.dto.user_dto.UserListResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;

import java.util.List;

public class ComFunc {

    /*
    * 작성일 : 2022-02-20
    * 작성자 : 김종완
    * 제목 : 유저 검사
    * 목적 : 로그인을 진행 한 유저일 경우 어떤 권한을 가진 유저인지 판단한다.
    *       만약, 관리자 권한으로 로그인 한 경우 관리자 페이지를 보여주기 위한 isAdmin 값을
    *       모델에 추가해준다.
    * */
    static public void checkUser(Model model, SessionUser user, User user_s){
        if (user != null) {
            model.addAttribute("nickname", user.getName());
            if(user.getRole().getKey().equals("ROLE_ADMIN"))
                model.addAttribute("isAdmin", user.getRole().getKey());
        }
        if (user_s != null) {
            model.addAttribute("nickname", user_s.getName());
            if(user_s.getRole().getKey().equals("ROLE_ADMIN"))
                model.addAttribute("isAdmin", user_s.getRole().getKey());
        }
    }

    static public List<ArticleListResponseDto> searchArticle(String search, String category, Pageable pageable, ArticleService articleService){

        List<ArticleListResponseDto> lst;
        if(search == null)
        {
            lst = articleService.findByCategory(category, pageable);
        }
        else
        {
            String[] type1 = search.split(":");
            if(type1[0].equals("title"))
                lst = articleService.findByCategoryAndTitleContaining(category, type1[1], pageable);
            else
                lst = articleService.findByCategoryAndUserIdContaining(category, type1[1], pageable);
        }
        return lst;
    }

    static public List<UserListResponseDto> searchUser(String search, Pageable pageable, UserService userService){
        List<UserListResponseDto> lst;
        if(search == null){
            lst = userService.findAll(pageable);
        }
        else
        {
            String[] type1 = search.split(":");
            if(type1[0].equals("email"))
                lst = userService.findByEmailContaining(type1[1], pageable);
            else
                lst = userService.findByNameContaining(type1[1], pageable);
        }
        return lst;
    }

    static public void hasSearchCondition(String url,String search, Model model){
        String[] type1 = null;
        if (search != null)
            type1 = search.split(":");
        if (type1 == null)
            model.addAttribute("url", url);
        else
            model.addAttribute("url", url+"/"+type1[0]+":"+type1[1]);
    }

}
