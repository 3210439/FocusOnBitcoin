package org.nell_nell.springboot.web;

import lombok.RequiredArgsConstructor;
import org.nell_nell.springboot.domain.user.User;
import org.nell_nell.springboot.service.user.UserService;
import org.nell_nell.springboot.web.dto.user_dto.UserRegisterDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 회원가입 Controller
 */
@Controller
@RequiredArgsConstructor
public class SignUpController {

    private final UserService userService;

    @GetMapping("/register")
    public String signup() {
        return "register";
    }

    @PostMapping("/register")
    public String signup(Model model,
                         @ModelAttribute UserRegisterDto userDto
    ) {
        userService.signup(userDto);
        String email = userDto.getUsername();
        User user = userService.findByEmail(email);

        if(user == null)
            model.addAttribute("SignupFail","");
        else
            model.addAttribute("SignupSuccess","");
        // 회원가입 후 로그인 페이지로 이동
        return "homeLogin";
    }
}