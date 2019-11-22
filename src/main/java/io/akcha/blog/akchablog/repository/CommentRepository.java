package io.akcha.blog.akchablog.repository;

import io.akcha.blog.akchablog.model.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.util.Streamable;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {

    Streamable<Comment> findAllByPostId(Integer id, Pageable pageable);
}
