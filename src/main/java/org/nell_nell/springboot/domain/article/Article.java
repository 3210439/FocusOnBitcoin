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

    @Column(nullable = false)
    private String user_id;

    @Column(nullable = false)
    private String category;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Long view_count;

    private String ip_address;

    @Builder
    public Article(String title, String content, String user_id, String ip_address, long view_count, String category) {
        this.title = title;
        this.content = content;
        this.user_id = user_id;
        this.ip_address = ip_address;
        this.view_count = view_count;
        this.category = category;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
