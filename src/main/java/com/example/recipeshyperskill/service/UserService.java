package com.example.recipeshyperskill.service;

import com.example.recipeshyperskill.model.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.recipeshyperskill.model.dto.UserDto;
import com.example.recipeshyperskill.model.mapper.UserMapper;
import com.example.recipeshyperskill.repository.UserRepository;
import com.example.recipeshyperskill.security.MyConfig;
import com.example.recipeshyperskill.security.UserDetailsImpl;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final MyConfig myConfig;
    private final UserMapper userMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailIgnoreCase(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
        return new UserDetailsImpl(user);
    }

    @Transactional
    public ResponseEntity<?> register(UserDto userDto) {
        if (userRepository.findByEmailIgnoreCase(userDto.getEmail()).isPresent()) {
            logger.warn("An account with the given email already exists! Email: {}", userDto.getEmail());
            return new ResponseEntity<>(Map.of(
                    "message", "An account with the given email already exists!"),
                    HttpStatus.BAD_REQUEST);
        }
        User user = userMapper.signUpToUser(userDto);
        user.setPassword(myConfig.passwordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        logger.info("User registered successfully. Email: {}", userDto.getEmail());
        return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
    }
}