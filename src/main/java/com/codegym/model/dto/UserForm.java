package com.codegym.model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserForm {
    private Long id;

    private String intro;

    private String work;

    private String address;

    private MultipartFile avatar;

    private MultipartFile cover;

    public UserForm() {
    }
}
