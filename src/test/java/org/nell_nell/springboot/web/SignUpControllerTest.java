package org.nell_nell.springboot.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nell_nell.springboot.domain.posts.Posts;
import org.nell_nell.springboot.domain.user.User;
import org.nell_nell.springboot.domain.user.UserRepository;
import org.nell_nell.springboot.service.user.UserService;
import org.nell_nell.springboot.web.dto.user_dto.UserRegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SignUpControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;


    @After
    public void tearDown() throws Exception {
        userRepository.deleteAll();
    }


    @Test
    @WithMockUser(roles="USER")
    public void 회원가입_테스트() throws Exception {
        // given
        UserRegisterDto userRegisterDto = UserRegisterDto.builder()
                .nickname("testuser01")
                .username("test@naver.com")
                .password("1234")
                .build();
        String url = "http://localhost:" + port + "/register";
        //when
        userService.signup(userRegisterDto);


        // then
        List<User> all = userRepository.findAll();
        assertThat(all.get(0).getName()).isEqualTo(userRegisterDto.getNickname());
        assertThat(all.get(0).getEmail()).isEqualTo(userRegisterDto.getUsername());
        /*의존성이 주입된passwordencoder가 반환하는 데이터와 service의 passwordencoder의 데이터가 다르다. */
        //assertThat(all.get(0).getPassword()).isEqualTo(passwordEncoder.encode(userRegisterDto.getPassword()));
    }
}