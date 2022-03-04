package org.nell_nell.springboot.domain.article;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.nell_nell.springboot.domain.BaseTimeEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Article extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(nullable = false)
    private String category;

    @Column(name="view_count", columnDefinition = "integer default 0", nullable = false)
    private Long viewCount;

    private String ip_address;

    @Builder
    public Article(String title, String content, String userId, String ip_address, long viewCount, String category) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.ip_address = ip_address;
        this.viewCount = viewCount;
        this.category = category;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
