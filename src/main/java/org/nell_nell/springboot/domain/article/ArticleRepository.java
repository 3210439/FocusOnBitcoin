package org.nell_nell.springboot.domain.article;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    //@Query("SELECT a FROM Article a WHERE a.category = 'humor' ORDER BY a.id DESC")
    //List<Article> findHumorBoard();

    List<Article> findByCategory(String category, Pageable pageable);

    List<Article> findTop4ByCategoryNotOrderByViewCountDesc(String category);

    //@Query("SELECT a FROM Article a WHERE a.category NOT IN ('announcement') LIMIT 4 ORDER BY a.viewCount DESC")
    //List<Article> popularArticle();

    List<Article> findTop2ByCategory(String category);

    List<Article> findByCategoryAndTitleContaining(String category, String title, Pageable pageable);
    List<Article> findByCategoryAndUserIdContaining(String category, String userId, Pageable pageable);

/*    @Query("SELECT a FROM Article a " +
            "WHERE " +
            "1=1 "
            + "and a.category LIKE (CASE WHEN :#{#need_val.category} = '' THEN '%%' ELSE  CONCAT('%',:#{#need_val.category},'%') END)"
            + "and a.title LIKE (CASE WHEN :#{#need_val.title} = '' THEN '%%' ELSE  CONCAT('%',:#{#need_val.title},'%') END)"
            + "and a.user_id LIKE (CASE WHEN :#{#need_val.user_id} = '' THEN '%%' ELSE  CONCAT('%',:#{#need_val.user_id},'%') END)")
    List<Article> findArticle(@Param("need_val") Article article, Pageable pageable);*/

    @Modifying
    @Query("update Article a set a.viewCount = a.viewCount + 1 where a.id = :id")
    int updateViewCount(Long id);

}
