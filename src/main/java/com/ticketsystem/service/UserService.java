package com.ticketsystem.service;

import com.ticketsystem.dto.request.UserCreationRequest;
import com.ticketsystem.dto.request.UserUpdateRequest;
import com.ticketsystem.dto.response.UserResponse;
import com.ticketsystem.dto.response.UserResponse1;
import com.ticketsystem.entity.Role;
import com.ticketsystem.entity.User;
import com.ticketsystem.mapper.UserMapper;
import com.ticketsystem.repository.RoleRepository;
import com.ticketsystem.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {
    UserMapper userMapper;
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    public User createUser(UserCreationRequest request) throws Exception {
        if (userRepository.existsByUsername(request.getUsername())){
            throw new Exception("User is existed");
        }

        User user;
        Role role = roleRepository.findByRoleName("USER");
        user = userMapper.toUser(request);
        user.setRole(role);
        user.setPasswordHash(passwordEncoder.encode(request.getPasswordHash()));
        log.info(user.getEmail());
        return userRepository.save(user);
    }

    public List<UserResponse> getUsers(){
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    public UserResponse1 getUser(int id){
        return userMapper.toUserResponse1(userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Id not found")));
    }
    public void deleteUser(int id){
        userRepository.deleteById(id);
    }

    public UserResponse updateUser(UserUpdateRequest request, int id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id not found"));
        userMapper.updateUser(user,request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public User getUserByUserName(String userName){
        return userRepository.findByUsername(userName)
                .orElseThrow(()->new RuntimeException("User name not found"));
    }
}
