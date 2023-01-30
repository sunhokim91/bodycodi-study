package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;

public class UserDto {
    @Getter
    public static class Request {
        private String userName;
        private String phone;
        private int userAge;
    }

    @Getter
    @Builder
    public static class Response {
        private int seqUser;
        private String userName;
        private String phone;
        private int userAge;
    }
}
