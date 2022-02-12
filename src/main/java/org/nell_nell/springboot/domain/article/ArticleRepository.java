package org.nell_nell.springboot.domain.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a WHERE a.category = 'humor' ORDER BY a.id DESC")
    List<Article> findHumorBoard();

    //@Query("SELECT a FROM Article a")
    //List<Article> findHumorBoard();
}