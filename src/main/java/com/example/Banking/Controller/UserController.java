package com.example.Banking.Controller;

import com.example.Banking.DTOs.AuthRequest;
import com.example.Banking.DTOs.UserDTO;
import com.example.Banking.Enums.Role;
import com.example.Banking.services.UserService;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.core.JacksonException;

import java.util.List;

@RestController
@CrossOrigin("localhost/4200")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        try {
            userDTO.setRole(Role.USER);
            return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/login")
    public void login(@RequestBody AuthRequest authRequest, HttpServletResponse response)
            throws IOException, JacksonException, java.io.IOException {
        String token = userService.login(authRequest);
        UserDTO userDTO = userService.getUserByEmail(authRequest.getUsername());
        response.getWriter().write(new JSONObject()
                .put("id", userDTO.getId()).put("token", token)
                .put("userName", userDTO.getUsername())
                .put("role",userDTO.getRole())
                .toString()
        );
    }
}
