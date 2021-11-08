package com.codegym.controller;

import com.codegym.model.dto.UserForm;
import com.codegym.model.entity.User;
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
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @Value("${file-upload}")
    private String fileUpload;

    @GetMapping("")
    public ResponseEntity<Iterable<User>> findAllUser() {
        Iterable<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> findUserById(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userOptional, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<User> editUserInfo(@PathVariable Long id, UserForm userForm) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            MultipartFile userFormCover = userForm.getCover();
            MultipartFile userFormAvatar = userForm.getAvatar();
            String coverFileName = userFormCover.getOriginalFilename();
            String avatarFileName = userFormAvatar.getOriginalFilename();
            try {
                FileCopyUtils.copy(userFormCover.getBytes(), new File(fileUpload + coverFileName));
                FileCopyUtils.copy(userFormAvatar.getBytes(), new File(fileUpload + avatarFileName));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            User user = new User();
            user.setId(userOptional.get().getId());
            user.setUsername(userOptional.get().getUsername());
            user.setPassword(userOptional.get().getPassword());
            user.setEmail(userOptional.get().getEmail());
            user.setIntro(userForm.getIntro());
            user.setWork(userForm.getWork());
            user.setAddress(userForm.getAddress());
            user.setCover(coverFileName);
            user.setAvatar(avatarFileName);
            return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
