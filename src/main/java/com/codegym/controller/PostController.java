package com.codegym.controller;


import com.codegym.model.entity.Post;
import com.codegym.model.entity.User;
import com.codegym.service.post.IPostService;
import com.codegym.service.user.IUserService;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import com.codegym.model.dto.PostForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Value("${file-upload}")
    private String fileUpload;

    @Autowired
    private IPostService postService;

    @Autowired
    private IUserService userService;


    @PostMapping("/save")
    public ResponseEntity<?> savePost(@RequestBody Post post) {
        return new ResponseEntity<>(postService.save(post), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findAllPostByUser(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Iterable<Post> postIterable = postService.findAllByUser(user.get());
            return new ResponseEntity<>(postIterable, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Post> createPost(PostForm postForm) {
        MultipartFile multipartFile = postForm.getImg();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(postForm.getImg().getBytes(), new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Post post = new Post();
        if(postForm.getId() != null) {
            post.setId(postForm.getId());
        }
        post.setUser(postForm.getUser());
        post.setContent(postForm.getContent());
        post.setImg(fileName);
        return new ResponseEntity<>(postService.save(post), HttpStatus.CREATED);
    }


    @GetMapping()
    public ResponseEntity<Iterable<Post>> getAllPost() {
        Iterable<Post> postOptional = postService.findAll();
        return new ResponseEntity<>(postOptional, HttpStatus.OK);
    }

    @GetMapping("/user/{id}/posts")
    public ResponseEntity<?> getPostByUserId(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Iterable<Post> postsByUser = postService.findAllByUser(user.get());
        return new ResponseEntity<>(postsByUser, HttpStatus.ACCEPTED);
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        Optional<Post> postOptional = postService.findById(id);
        if (!postOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(postOptional.get(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> editPost(@PathVariable Long id, PostForm postForm) {
        Optional<Post> postOptional = postService.findById(id);
        if (!postOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        postForm.setId(postOptional.get().getId());
        return createPost(postForm);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Post> delPost(@PathVariable Long id){
        Optional<Post> postOptional = postService.findById(id);
        if(!postOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        postService.remove(postOptional.get().getId());
       return new ResponseEntity<>(HttpStatus.OK);
    }
}