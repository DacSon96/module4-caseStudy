package com.codegym.service.user;

import com.codegym.model.entity.User;
import com.codegym.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends IGeneralService<User>, UserDetailsService {
    User findByUsername(String username);

    User findByEmail(String email);
}
