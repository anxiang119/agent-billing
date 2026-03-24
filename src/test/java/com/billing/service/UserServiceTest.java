package com.billing.service;

import com.billing.entity.User;
import com.billing.exception.BusinessException;
import com.billing.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setTenantId(10L);
        testUser.setUsername("testuser");
        testUser.setPassword("encrypted_password");
        testUser.setEmail("test@example.com");
        testUser.setStatus("ACTIVE");
        testUser.setCreatedTime(LocalDateTime.now());
        testUser.setUpdatedTime(LocalDateTime.now());
    }

    @Test
    void testCreateUser_Success() {
        User newUser = new User();
        newUser.setTenantId(10L);
        newUser.setUsername("newuser");
        newUser.setPassword("encrypted_password");
        newUser.setEmail("new@example.com");

        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(2L);
            return user;
        });

        User result = userService.createUser(newUser);

        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("newuser", result.getUsername());
        verify(userRepository).existsByUsername("newuser");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testCreateUser_DuplicateUsername() {
        User newUser = new User();
        newUser.setUsername("testuser");

        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            userService.createUser(newUser);
        });

        assertTrue(exception.getMessage().contains("用户名已存在"));
        verify(userRepository).existsByUsername("testuser");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testGetUserById_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("testuser", result.getUsername());
        verify(userRepository).findById(1L);
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            userService.getUserById(999L);
        });

        assertTrue(exception.getMessage().contains("用户不存在"));
        verify(userRepository).findById(999L);
    }

    @Test
    void testGetUserByUsername_Success() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        User result = userService.getUserByUsername("testuser");

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userRepository).findByUsername("testuser");
    }

    @Test
    void testGetUsersByTenantId_Success() {
        User user2 = new User();
        user2.setId(2L);
        user2.setTenantId(10L);

        List<User> users = Arrays.asList(testUser, user2);
        when(userRepository.findByTenantId(10L)).thenReturn(users);

        List<User> result = userService.getUsersByTenantId(10L);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository).findByTenantId(10L);
    }

    @Test
    void testUpdateUser_Success() {
        User updateUser = new User();
        updateUser.setEmail("new@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.updateUser(1L, updateUser);

        assertNotNull(result);
        assertEquals("new@example.com", result.getEmail());
        verify(userRepository).findById(1L);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testUpdateUser_NotFound() {
        User updateUser = new User();
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            userService.updateUser(999L, updateUser);
        });

        assertTrue(exception.getMessage().contains("用户不存在"));
        verify(userRepository).findById(999L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        doNothing().when(userRepository).delete(testUser);

        userService.deleteUser(1L);

        verify(userRepository).findById(1L);
        verify(userRepository).delete(testUser);
    }

    @Test
    void testValidateUser_Success() {
        when(userRepository.existsByIdAndActive(1L)).thenReturn(true);

        boolean result = userService.validateUser(1L);

        assertTrue(result);
        verify(userRepository).existsByIdAndActive(1L);
    }

    @Test
    void testValidateUser_Inactive() {
        when(userRepository.existsByIdAndActive(1L)).thenReturn(false);

        boolean result = userService.validateUser(1L);

        assertFalse(result);
        verify(userRepository).existsByIdAndActive(1L);
    }

    @Test
    void testUpdateLastLoginTime_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).updateLastLoginTime(eq(1L), any(LocalDateTime.class));

        userService.updateLastLoginTime(1L);

        verify(userRepository).existsById(1L);
        verify(userRepository).updateLastLoginTime(eq(1L), any(LocalDateTime.class));
    }
}
