package com.codegym.controller;


import com.codegym.model.entity.Post;
import com.codegym.model.entity.User;
import com.codegym.service.post.IPostService;
import com.codegym.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
     @Autowired
    private IUserService userService;

     @Autowired
     private IPostService postService;

     @GetMapping("/{id}")
    public ResponseEntity<Iterable<Post>> getMyPost(@PathVariable Long id){
         Optional<User> user = userService.findById(id);
         if (!user.isPresent()){
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
         Iterable<Post> postIterable = postService.findAllByUser(user.get());
         return new ResponseEntity<>(postIterable,HttpStatus.ACCEPTED);
     }


}
