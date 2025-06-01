package com.order.processing.service;

import com.order.processing.domain.User;

import java.util.Collection;
import java.util.List;

public interface UserService {

    List<User> saveAllUsers(Collection<User> users);
}
