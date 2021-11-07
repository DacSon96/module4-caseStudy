package com.codegym.controller;

import com.codegym.exception.RePasswordNotCorrectException;
import com.codegym.model.dto.LoginForm;
import com.codegym.model.dto.RegistrationForm;
import com.codegym.model.entity.Role;
import com.codegym.model.entity.User;
import com.codegym.model.dto.JwtResponse;
import com.codegym.service.jwt.JwtService;
import com.codegym.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@CrossOrigin("*")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginForm loginForm) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findByUsername(loginForm.getUsername()).get();
        return ResponseEntity.ok(new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), currentUser.getEmail(), userDetails.getAuthorities()));
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationForm> register(@Valid @RequestBody RegistrationForm registrationForm) throws RePasswordNotCorrectException {
        if (!registrationForm.getPassword().equals(registrationForm.getRePassword())) {
            throw new RePasswordNotCorrectException();
        }
        User user = new User();
        user.setUsername(registrationForm.getUsername());
        user.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
        user.setEmail(registrationForm.getEmail());
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(2L, "ROLE_USER"));
        user.setRoles(roles);
        user.setIntro("Your bio ...");
        user.setWork("Your work ...");
        user.setAddress("Your address ...");
        user.setAvatar("/images/default-avatar.png");
        user.setCover("/images/default-cover.png");
        userService.save(user);
        return new ResponseEntity<>(registrationForm, HttpStatus.OK);
    }
}
