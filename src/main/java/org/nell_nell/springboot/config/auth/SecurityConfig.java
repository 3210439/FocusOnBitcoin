package org.nell_nell.springboot.config.auth;

import lombok.RequiredArgsConstructor;
import org.nell_nell.springboot.domain.user.Role;
import org.nell_nell.springboot.domain.user.User;
import org.nell_nell.springboot.service.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final UserService userService;
    private String[] pathArray = new String[]{"/altCoinBoard","/majorCoinBoard","/altBoard"
            ,"/majorBoard/**","/freeBoard","/QnA","/humorBoard","/register"
            ,"/homeLogin","/","/index", "/css/**","/img/**","/scss/**","/vendor/**"
            , "/images/**", "/js/**", "/h2-console/**", "/profile","/article/select/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers(pathArray).permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .antMatchers("/user/**").hasRole(Role.ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

        // login
        http.formLogin()
                .loginPage("/homeLogin")
                .defaultSuccessUrl("/")
                .permitAll(); // 모두 허용
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userService.findByEmail(username);
            if (user == null) {
                throw new UsernameNotFoundException(username);
            }
            return user;
        };
    }
}