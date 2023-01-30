package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/save")
    public ResponseEntity<HttpStatus> saveUser(@RequestBody UserDto.Request request) {
        User user = createDtoToDomain(request);
        userService.saveUser(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public UserDto.Response findUser(@PathVariable int id) {
        User user = userService.findUser(id);

        return createDomainToDto(user);
    }

    @PatchMapping("/modify")
    public ResponseEntity<HttpStatus> modifyUser(UserDto.Request request) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<HttpStatus> removeUser(@PathVariable int id) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private User createDtoToDomain(UserDto.Request request) {
        return User.builder()
                .userAge(request.getUserAge())
                .phone(request.getPhone())
                .userName(request.getUserName())
                .build();
    }

    private UserDto.Response createDomainToDto(User user) {
        return UserDto.Response
                .builder()
                .seqUser(user.getSeqUser())
                .userName(user.getUserName())
                .phone(user.getPhone())
                .userAge(user.getUserAge())
                .build();
    }
}
