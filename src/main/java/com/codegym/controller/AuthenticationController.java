package com.codegym.controller;

import com.codegym.exception.RePasswordException;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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
        User currentUser = userService.findByUsername(loginForm.getUsername());
        return ResponseEntity.ok(new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), currentUser.getEmail(), userDetails.getAuthorities()));
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationForm> register(@Valid @RequestBody RegistrationForm registrationForm, BindingResult bindingResult) throws RePasswordException {
        if (!registrationForm.getPassword().equals(registrationForm.getRePassword())) {
            throw new RePasswordException();
        } else if (!bindingResult.hasFieldErrors()) {
            User user = new User();
            user.setUsername(registrationForm.getUsername());
            user.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
            user.setEmail(registrationForm.getEmail());
            List<Role> roles = new ArrayList<>();
            roles.add(new Role(2L, "ROLE_USER"));
            user.setRoles(roles);
            userService.save(user);
        }
        return new ResponseEntity<>(registrationForm, HttpStatus.OK);
    }
}