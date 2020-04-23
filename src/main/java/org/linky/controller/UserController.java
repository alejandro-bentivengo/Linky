package org.linky.controller;

import org.linky.exceptions.AbstractBaseException;
import org.linky.model.services.UserRequest;
import org.linky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity newUser(@RequestBody UserRequest userRequest) {
        try {
            userService.newUser(userRequest);
            return ResponseEntity.ok().build();
        } catch (AbstractBaseException e) {
            return e.buildResponse();
        }
    }

}
