package org.nell_nell.springboot.domain.article;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    //@Query("SELECT a FROM Article a WHERE a.category = 'humor' ORDER BY a.id DESC")
    //List<Article> findHumorBoard();

    List<Article> findByCategory(String category, Pageable pageable);

    @Modifying
    @Query("update Article a set a.view_count = a.view_count + 1 where a.id = :id")
    int updateViewCount(Long id);

}
