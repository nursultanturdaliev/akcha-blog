package io.akcha.blog.akchablog.controller;

import java.util.List;

import io.akcha.blog.akchablog.PostService;
import io.akcha.blog.akchablog.model.Post;
import io.akcha.blog.akchablog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api")
public class PostController {

    private PostRepository postRepository;

    private PostService postService;

    @Autowired
    public PostController(PostRepository postRepository, PostService postService) {
        this.postRepository = postRepository;
        this.postService = postService;
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<Post> fetchPost(@PathVariable Long id) {
        Optional<Post> optionalPost = postService.findById(id);
        if (optionalPost.isPresent()) {
            return ResponseEntity.ok(optionalPost.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/post")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post createdPost = postRepository.save(post);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @GetMapping("/post")
    public ResponseEntity<Iterable<Post>> fetchPosts(
            @RequestParam Integer limit,
            @RequestParam Integer offset,
            @RequestParam String sortProperty,
            @RequestParam Sort.Direction direction) {

        List<Post> posts = postService.fetchPosts(limit, offset, sortProperty, direction);

        return ResponseEntity.ok(posts);
    }
}
