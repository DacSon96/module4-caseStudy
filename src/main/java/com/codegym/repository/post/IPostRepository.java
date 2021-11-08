package com.codegym.repository.post;

import com.codegym.model.entity.Post;
import com.codegym.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPostRepository extends JpaRepository<Post,Long> {

    Iterable<Post> findAllByUser(User user);
}
