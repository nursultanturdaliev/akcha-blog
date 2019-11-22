package io.akcha.blog.akchablog.unit;

import io.akcha.blog.akchablog.PostService;
import io.akcha.blog.akchablog.controller.PostController;
import io.akcha.blog.akchablog.model.Post;
import io.akcha.blog.akchablog.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class PostControllerTest {

    @Test
    public void testFetchPostNotFound() {
        PostRepository postRepository = Mockito.mock(PostRepository.class);
        PostService postService = Mockito.mock(PostService.class);

        PostController postController = new PostController(postRepository, postService);

        ResponseEntity<Post> responseEntity = postController.fetchPost(1L);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testFetchPostFound() {
        PostRepository postRepository = Mockito.mock(PostRepository.class);

        PostService postService = Mockito.mock(PostService.class);

        Post post = new Post();

        Optional<Post> optionalPost = Optional.of(post);

        when(postRepository.findById(anyLong())).thenReturn(optionalPost);

        PostController postController = new PostController(postRepository, postService);

        ResponseEntity<Post> responseEntity = postController.fetchPost(101L);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(post);
    }

    @Test
    public void fetchPostsEmpty() {
        PostRepository postRepository = Mockito.mock(PostRepository.class);
        PostService postService = Mockito.mock(PostService.class);
        PostController postController = new PostController(postRepository, postService);


        Integer limit = 10;
        Integer offset = 0;
        String sortProperty = "id";
        Sort.Direction direction = Sort.Direction.ASC;

        ResponseEntity<Iterable<Post>> responseEntity = postController.fetchPosts(limit, offset, sortProperty, direction);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEmpty();
    }

    @Test
    public void fetchPostsNotEmpty() {
        PostRepository postRepository = Mockito.mock(PostRepository.class);
        PostService postService = Mockito.mock(PostService.class);

        List<Post> posts = new ArrayList<>();
        Post post = new Post();
        posts.add(post);

        when(postService.fetchPosts(anyInt(),anyInt(),anyString(), any(Sort.Direction.class))).thenReturn(posts);

        PostController postController = new PostController(postRepository, postService);

        Integer limit = 10;
        Integer offset = 0;
        String sortProperty = "id";
        Sort.Direction direction = Sort.Direction.ASC;

        ResponseEntity<Iterable<Post>> responseEntity = postController.fetchPosts(limit, offset, sortProperty, direction);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotEmpty();

        assertThat(responseEntity.getBody()).contains(post);
    }


}
