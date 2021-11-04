package com.codegym.service.post;

import com.codegym.model.entity.Post;
import com.codegym.model.entity.User;
import com.codegym.service.IGeneralService;

public interface IPostService extends IGeneralService<Post> {
    Iterable<Post> findAllByUser(User user);
}
