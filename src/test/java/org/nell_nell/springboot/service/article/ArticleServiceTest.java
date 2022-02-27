package org.nell_nell.springboot.service.article;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nell_nell.springboot.domain.article.Article;
import org.nell_nell.springboot.domain.article.ArticleRepository;
import org.nell_nell.springboot.domain.posts.Posts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServiceTest {

    @Autowired
    ArticleRepository articleRepository;

    @After
    public void cleanup() {
        articleRepository.deleteAll();
    }

    @Test
    public void 글_등록_테스트() {

        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";
        String user = "user1";
        String category = "humor";

        articleRepository.save(Article.builder()
                .title(title)
                .content(content)
                .user_id(user)
                .category(category)
                .build());

        //when
        List<Article> postsList = articleRepository.findAll();

        //then
        Article article = postsList.get(0);
        assertThat(article.getTitle()).isEqualTo(title);
        assertThat(article.getContent()).isEqualTo(content);
        assertThat(article.getCategory()).isEqualTo(category);
        assertThat(article.getUser_id()).isEqualTo(user);
    }
}