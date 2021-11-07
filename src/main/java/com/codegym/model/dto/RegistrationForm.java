package com.codegym.model.dto;

import com.codegym.exception.UniqueEmail;
import com.codegym.exception.UniqueUsername;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RegistrationForm {
    @UniqueUsername
    @NotEmpty
    @Size(min = 4, max = 20)
    private String username;

    @NotEmpty
    @Size(min = 4, max = 20)
    private String password;

    @NotEmpty
    @Size(min = 4, max = 20)
    private String rePassword;

    @UniqueEmail
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$", message = "* invalid email")
    private String email;

    private String intro;

    private String work;

    private String address;

    private String avatar;

    private String cover;
}
