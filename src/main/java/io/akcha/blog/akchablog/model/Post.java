package io.akcha.blog.akchablog.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(generator = "native", strategy = GenerationType.IDENTITY)
    Long id;

    private String title;

    @Column(columnDefinition = "text")
    private String shortDescription;

    @Column(columnDefinition = "text")
    private String longDescription;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "post")
    List<Comment> comments;

    @ManyToOne()
    private User author;
}
