package com.order.processing.service.impl;

import com.order.processing.domain.User;
import com.order.processing.repository.UserRepository;
import com.order.processing.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> saveAllUsers(Collection<User> users) {
        return userRepository.saveAllAndFlush(users);
    }

}
