package com.awsfinal.awsfinalvalidations.controllers;

import com.awsfinal.awsfinalvalidations.DTOs.UserDTO;
import com.awsfinal.awsfinalvalidations.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/new")
    public ResponseEntity<UserDTO> validateBody(@RequestBody UserDTO userDTO) {
        return userService.validate(userDTO);
    }

}
