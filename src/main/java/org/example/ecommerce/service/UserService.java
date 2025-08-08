package org.example.ecommerce.service;

import org.example.ecommerce.model.User;

import java.util.Optional;

public interface UserService {

    User savedUser(User user);


    Optional<User> findByUsername(String  username);
}
