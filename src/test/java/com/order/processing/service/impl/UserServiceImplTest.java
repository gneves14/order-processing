package com.order.processing.service.impl;

import com.order.processing.domain.User;
import com.order.processing.domain.UserBuilder;
import com.order.processing.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return a list of saved users")
    void shouldReturnSavedUsersWhenGivenValidCollection() {
        User user1 = UserBuilder.builder()
                .userId(1)
                .name("Test 1")
                .build();

        User user2 = UserBuilder.builder()
                .userId(2)
                .name("Test 2")
                .build();

        List<User> usersCollection = List.of(user1, user2);

        when(userRepository.saveAllAndFlush(usersCollection)).thenReturn(usersCollection);

        List<User> usersList = userService.saveAllUsers(usersCollection);

        verify(userRepository, times(1)).saveAllAndFlush(usersCollection);
        assertEquals(usersCollection, usersList);
    }

    @Test
    @DisplayName("Should return empty list when saving an empty collection of users")
    void shouldReturnEmptyListWhenGivenEmptyCollection() {
        List<User> emptyList = List.of();

        when(userRepository.saveAllAndFlush(emptyList)).thenReturn(emptyList);

        List<User> result = userService.saveAllUsers(emptyList);

        assertTrue(result.isEmpty());
    }
}