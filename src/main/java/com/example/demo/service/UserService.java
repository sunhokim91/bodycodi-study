package com.example.demo.service;

import com.example.demo.domain.User;

public interface UserService {
    void saveUser(User user);
    User findUser(int id);
    void modifyUser(User user);
    void removeUser(User user);
}
