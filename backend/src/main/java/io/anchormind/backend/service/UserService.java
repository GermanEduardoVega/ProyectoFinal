package io.anchormind.backend.service;

import io.anchormind.backend.model.entity.User;
import io.anchormind.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserByUsername(String username){
        return userRepository.findActiveByUsername(username);

    }


}
