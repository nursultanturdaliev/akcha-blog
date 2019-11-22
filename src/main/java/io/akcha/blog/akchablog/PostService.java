package io.akcha.blog.akchablog;

import io.akcha.blog.akchablog.model.Post;
import io.akcha.blog.akchablog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {


    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public List<Post> fetchPosts(Integer limit, Integer offset, String sortProperty, Sort.Direction direction) {

        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, sortProperty));

        return postRepository.findAll(pageRequest).toList();
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }
}
