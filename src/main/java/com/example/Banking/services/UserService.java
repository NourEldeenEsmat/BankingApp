package com.example.Banking.services;

import com.example.Banking.DTOs.AuthRequest;
import com.example.Banking.DTOs.UserDTO;
import com.example.Banking.Entities.User;
import com.example.Banking.Enums.AccountType;
import com.example.Banking.Mapper.UserMapper;
import com.example.Banking.Reposetries.UserRepository;
import com.example.Banking.securety.utill.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public List<UserDTO> getAllUsers() {
        return userRepo.findAll().stream().map(UserMapper::toDto).toList();
    }

    public UserDTO getUserById(Long id) {
        Optional<User> user = userRepo.findById(id);
        return UserMapper.toDto(user.get());
    }

    public UserDTO createUser(UserDTO userDTO) {
        if(userRepo.existsByEmail(userDTO.getEmail())){
            throw new RuntimeException("user founded");
        }
        User user = userRepo.save(UserMapper.fromDto(userDTO));
        accountService.createAccount(user.getId(),AccountType.CURRENT);
        return UserMapper.toDto(user);
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    public UserDTO getUserByEmail(String email) {
        return UserMapper.toDto(userRepo.findByEmail(email));
    }

    public String login(AuthRequest request) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        return jwtUtils.generateToken(authentication.getName());
    }
}