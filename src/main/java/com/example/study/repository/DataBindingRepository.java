package com.example.study.repository;

import com.example.study.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DataBindingRepository {
    void saveUserBindingProcess(User user);
    void saveUserNotBindingProcess(User user);
}
