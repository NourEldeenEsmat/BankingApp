package com.example.Banking.Mapper;

import com.example.Banking.DTOs.UserDTO;
import com.example.Banking.Entities.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserMapper {
    public static UserDTO toDto(User user ){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword("hidden password");
        userDTO.setRole(user.getRole());
        return userDTO;
    }
    public static User fromDto(UserDTO userDTO){
        User user = new User();
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getUsername());
        user.setRole(userDTO.getRole());
        return user;
    }
}
