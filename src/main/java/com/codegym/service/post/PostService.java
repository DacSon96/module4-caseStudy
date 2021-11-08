package com.codegym.service.post;

import com.codegym.model.entity.Post;
import com.codegym.model.entity.User;
import com.codegym.repository.post.IPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService implements IPostService {
    @Autowired
    IPostRepository postRepository;

    @Override
    public Iterable<Post> findAll() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void remove(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Iterable<Post> findAllByUser(User user) {
        return postRepository.findAllByUserOrderByIdDesc(user);
    }
}
