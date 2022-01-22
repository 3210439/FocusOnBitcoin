package org.nell_nell.springboot.service.article;

import lombok.RequiredArgsConstructor;
import org.nell_nell.springboot.domain.article.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;


}
