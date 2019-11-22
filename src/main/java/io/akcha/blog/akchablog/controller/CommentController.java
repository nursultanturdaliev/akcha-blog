package io.akcha.blog.akchablog.controller;

import java.util.List;

import io.akcha.blog.akchablog.model.Comment;
import io.akcha.blog.akchablog.model.Post;
import io.akcha.blog.akchablog.repository.CommentRepository;
import io.akcha.blog.akchablog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/post/{id}/comment")
    public ResponseEntity<Comment> create(@PathVariable Long id, @RequestBody Comment comment) {

        Optional<Post> optionalPost = postRepository.findById(id);

        if (!optionalPost.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        comment.setPost(optionalPost.get());

        Comment createComment = commentRepository.save(comment);

        return ResponseEntity.status(HttpStatus.CREATED).body(createComment);
    }

    @GetMapping("/post/{id}/comments")
    public ResponseEntity<Iterable<Comment>> fetchPostComments(
            @PathVariable Integer id,
            @RequestParam Integer limit,
            @RequestParam Integer offset
    ) {

        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "id"));

        List<Comment> comments = commentRepository.findAllByPostId(id, pageRequest).toList();

        return ResponseEntity.ok(comments);

    }
}
