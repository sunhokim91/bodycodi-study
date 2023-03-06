package com.example.study.controller;

import com.example.study.domain.User;
import com.example.study.service.DataBindingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/data/binding")
@RequiredArgsConstructor
public class DataBindingController {

    private final DataBindingService dataBindingService;

    @PostMapping("/yes")
    public void saveUserBindingProcess(@RequestBody List<User> user) {
        dataBindingService.saveUserBindingProcess(user);
    }

    @PostMapping("/no")
    public void saveUserNotBindingProcess(@RequestBody List<User> user) {
        dataBindingService.saveUserNotBindingProcess(user);
    }
}
