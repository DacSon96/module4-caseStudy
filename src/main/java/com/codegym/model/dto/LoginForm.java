package com.codegym.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class LoginForm {
    @NotEmpty
    @Size(min = 5, max = 20)
    private String username;

    @NotEmpty
    @Size(min = 5, max = 20)
    private String password;
}
