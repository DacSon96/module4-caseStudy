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

    @GetMapping
    public ResponseEntity<Iterable<User>> findAllUser() {
        List<User> users = (List<User>) userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> editUserInfo(@PathVariable Long id, UserForm userForm) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            MultipartFile userFormAvatar = userForm.getAvatar();
            MultipartFile userFormCover = userForm.getCover();
            String avatarFileName = userFormAvatar.getOriginalFilename();
            String coverFileName = userFormCover.getOriginalFilename();
            if (avatarFileName.equals("")){
                avatarFileName = userOptional.get().getAvatar();
            }else {
                try {
                    FileCopyUtils.copy(userFormAvatar.getBytes(), new File(fileUpload + avatarFileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (coverFileName.equals("")){
                coverFileName = userOptional.get().getCover();
            }else {
                try {
                    FileCopyUtils.copy(userFormCover.getBytes(), new File(fileUpload + coverFileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }

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
