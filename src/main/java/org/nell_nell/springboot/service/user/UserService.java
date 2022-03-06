package org.nell_nell.springboot.service.user;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.nell_nell.springboot.domain.article.Article;
import org.nell_nell.springboot.domain.user.AlreadyRegisteredUserException;
import org.nell_nell.springboot.domain.user.Role;
import org.nell_nell.springboot.domain.user.User;
import org.nell_nell.springboot.domain.user.UserRepository;
import org.nell_nell.springboot.web.dto.article_dto.ArticleResponseDto;
import org.nell_nell.springboot.web.dto.article_dto.ArticleUpdateRequestDto;
import org.nell_nell.springboot.web.dto.user_dto.UserListResponseDto;
import org.nell_nell.springboot.web.dto.user_dto.UserRegisterDto;
import org.nell_nell.springboot.web.dto.user_dto.UserResponseDto;
import org.nell_nell.springboot.web.dto.user_dto.UserUpdateRequestDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User signup(
            UserRegisterDto userDto
    ){
        if (!userRepository.findByEmail(userDto.getUsername()).equals(Optional.empty())) {
            throw new AlreadyRegisteredUserException();
        }
        if (!userRepository.findByName(userDto.getUsername()).equals(Optional.empty())){
            throw new AlreadyRegisteredUserException();
        }
        String str = passwordEncoder.encode(userDto.getPassword());
        User user = User.builder()
                .name(userDto.getNickname())
                .email(userDto.getUsername())
                .pw(passwordEncoder.encode(userDto.getPassword()))
                .role(Role.GUEST)
                .picture("")
                .build();
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email){
        return userRepository.findByEmail(email).get();
    }

    @Transactional(readOnly = true)
    public List<UserListResponseDto> findByEmailContaining(String email, Pageable pageable){
        final List<UserListResponseDto> collect = userRepository.findByEmailContaining(email, pageable).stream().
                map(UserListResponseDto::new).collect(Collectors.toList());
        return collect;
    }

    @Transactional(readOnly = true)
    public List<UserListResponseDto> findByNameContaining(String name, Pageable pageable){
        final List<UserListResponseDto> collect = userRepository.findByNameContaining(name, pageable).stream().
                map(UserListResponseDto::new).collect(Collectors.toList());
        return collect;
    }

    @Transactional(readOnly = true)
    public List<UserListResponseDto> findAll(Pageable pageable){
        final List<UserListResponseDto> collect = userRepository.findAll(pageable).stream().
        map(UserListResponseDto::new).collect(Collectors.toList());
        return collect;
    }

    @Transactional
    public Long update(Long id, UserUpdateRequestDto requestDto){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. "+id));

        user.update(requestDto.getName(), requestDto.getPicture(), requestDto.getRole());

        return id;
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) {
        User entity = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."+id));
        return new UserResponseDto(entity);
    }

    @Transactional
    public void delete (Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."+id));

        userRepository.delete(user);
    }

}
