package org.nell_nell.springboot.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signup(
            String name_,
            String email_,
            String pw_
    ){
        if (userRepository.findByEmail(email_) != null) {
            throw new AlreadyRegisteredUserException();
        }
        User user = User.builder()
                .name(name_)
                .email(email_)
                .pw(passwordEncoder.encode(pw_))
                .role(Role.GUEST)
                .build();
        return userRepository.save(user);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email).get();
    }
}
