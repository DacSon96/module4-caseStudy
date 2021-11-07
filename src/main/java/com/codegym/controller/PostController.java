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
@RequestMapping("/post")
public class PostController {
    @Autowired
    private IPostService postService;

    @Autowired
    private IUserService userService;

    @GetMapping("")
    public ResponseEntity<Iterable<Post>> getAllPost() {
        Iterable<Post> postOptional = postService.findAll();
        return new ResponseEntity<>(postOptional, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> savePost(@RequestBody Post post) {
        return new ResponseEntity<>(postService.save(post), HttpStatus.CREATED);
    }
    @GetMapping("{userName}")
    public ResponseEntity<?> findAllPostByUser(@PathVariable String userName) {
        Optional<User> user = userService.findByUsername(userName);
        Iterable<Post> postIterable= postService.findAllByUser(user.get());
        return new ResponseEntity<>(postIterable, HttpStatus.OK);
    }

}
