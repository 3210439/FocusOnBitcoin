package org.nell_nell.springboot.service.posts;

import lombok.RequiredArgsConstructor;
import org.nell_nell.springboot.domain.posts.Posts;
import org.nell_nell.springboot.domain.posts.PostsRepository;
import org.nell_nell.springboot.web.dto.PostsSaveRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }
}
