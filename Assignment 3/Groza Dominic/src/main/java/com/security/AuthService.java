package com.security;

import com.security.dto.SignupRequest;
import com.user.UserRepository;
import com.user.dto.UserListDto;
import com.user.model.User;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    private final PasswordEncoder encoder;


    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void register(SignupRequest signUpRequest) {
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .password(encoder.encode(signUpRequest.getPassword()))
                .email(signUpRequest.getEmail())
                .address(signUpRequest.getAddress())
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .build();
        sendMail(user);

        userRepository.save(user);
    }
    public void sendMail(User user){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Welcome to the better facebook");
        message.setText("Welcome, "+user.getUsername());
        System.out.println(user.getEmail());
        message.setTo(user.getEmail());
        mailSender.send(message);

    }
}