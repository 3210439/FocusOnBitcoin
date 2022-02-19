package org.nell_nell.springboot.service.user;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.nell_nell.springboot.domain.user.AlreadyRegisteredUserException;
import org.nell_nell.springboot.domain.user.Role;
import org.nell_nell.springboot.domain.user.User;
import org.nell_nell.springboot.domain.user.UserRepository;
import org.nell_nell.springboot.web.dto.user_dto.UserRegisterDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signup(
            UserRegisterDto userDto
    ){
        if (!userRepository.findByEmail(userDto.getUsername()).equals(Optional.empty())) {
            throw new AlreadyRegisteredUserException();
        }
        String str = passwordEncoder.encode(userDto.getPassword());
        User user = User.builder()
                .name(userDto.getNickname())
                .email(userDto.getUsername())
                .pw(passwordEncoder.encode(userDto.getPassword()))
                .role(Role.GUEST)
                .build();
        return userRepository.save(user);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email).get();
    }
}
