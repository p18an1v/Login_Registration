package com.LoginRegisterAuth.Service;

import com.LoginRegisterAuth.Model.User;
import com.LoginRegisterAuth.Repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userService {
    @Autowired
    private userRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public boolean userExists(String username, String email) {
        return userRepository.existsByUsername(username) || userRepository.existsById(email);
    }

    public User findByEmail(String email) {
        return userRepository.findById(email).orElse(null);
    }
}
