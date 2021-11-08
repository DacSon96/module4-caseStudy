package com.codegym.repository;

import com.codegym.model.entity.Post;
import com.codegym.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostRepository extends JpaRepository<Post,Long> {
    Iterable<Post> findAllByUserOrderByIdDesc(User user);

}
