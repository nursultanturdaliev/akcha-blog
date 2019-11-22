package io.akcha.blog.akchablog.repository;

import io.akcha.blog.akchablog.model.Post;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
}
