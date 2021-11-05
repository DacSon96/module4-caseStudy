package com.codegym.controller;


import com.codegym.model.entity.Post;
import com.codegym.model.entity.User;
import com.codegym.repository.IUserRepository;
import com.codegym.service.post.IPostService;
import com.codegym.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private IPostService postService;

    @Autowired
    private IUserService userService;

    @ModelAttribute
    public Iterable<User> users() {
        return userService.findAll();
    }

    @GetMapping()
    public ResponseEntity<Iterable<Post>> getAllPost() {
        Iterable<Post> postOptional = postService.findAll();
        return new ResponseEntity<>(postOptional, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getPostByUserId(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Iterable<Post> postsByUser = postService.findAllByUser(user.get());
        return new ResponseEntity<>(postsByUser, HttpStatus.ACCEPTED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        Optional<Post> post = postService.findById(id);
        return new ResponseEntity<>(post.get(), HttpStatus.OK);
    }
}
