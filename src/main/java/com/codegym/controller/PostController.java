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
import org.springframework.security.core.parameters.P;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Value("${file-upload}")
    private String fileUpload;

    @Autowired
    private IPostService postService;


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
