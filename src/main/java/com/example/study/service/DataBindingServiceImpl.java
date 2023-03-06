package com.example.study.service;

import com.example.study.domain.User;
import com.example.study.repository.DataBindingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataBindingServiceImpl implements DataBindingService {

    private final DataBindingRepository dataBindingRepository;

    @Override
    public void saveUserBindingProcess(List<User> users) {
        long startTime = System.currentTimeMillis();
        for(User user : users) {
            dataBindingRepository.saveUserBindingProcess(user);
        }
        System.out.println("Binding Process Time = " + (System.currentTimeMillis() - startTime));
    }

    @Override
    public void saveUserNotBindingProcess(List<User> users) {
        long startTime = System.currentTimeMillis();
        for(User user : users) {
            dataBindingRepository.saveUserNotBindingProcess(user);
        }
        System.out.println("Not Binding Process Time = " + (System.currentTimeMillis() - startTime));
    }
}
