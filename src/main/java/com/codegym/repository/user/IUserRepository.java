package com.codegym.repository.user;

import com.codegym.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);
}
