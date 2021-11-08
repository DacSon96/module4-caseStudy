package com.codegym.controller;

import com.codegym.model.dto.PostForm;
import com.codegym.model.entity.Post;
import com.codegym.model.entity.User;
import com.codegym.service.post.IPostService;
import com.codegym.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private IPostService postService;

    @Autowired
    private IUserService userService;

    @Value("${file-upload}")
    private String fileUpload;

    @GetMapping("")
    public ResponseEntity<Iterable<Post>> getAllPost() {
        Iterable<Post> postIterable = postService.findAll();
        return new ResponseEntity<>(postIterable, HttpStatus.OK);
    }


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
        post.setUser(postForm.getUser());
        post.setContent(postForm.getContent());
        post.setImg(fileName);
        return new ResponseEntity<>(postService.save(post), HttpStatus.CREATED);
    }
}