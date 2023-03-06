package com.example.study.service;

import com.example.study.domain.User;

import java.util.List;

public interface DataBindingService {
    void saveUserBindingProcess(List<User> user);
    void saveUserNotBindingProcess(List<User> user);
}
