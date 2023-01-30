package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findUser(int seqUser) {
        return userRepository.findById(seqUser).orElse(null);
    }

    @Override
    public void modifyUser(User user) {
    }

    @Override
    public void removeUser(User user) {

    }
}