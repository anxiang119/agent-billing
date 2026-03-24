package com.billing.repository;

import com.billing.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * 用户Repository测试类
 */
@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

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
    void testFindById() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // Act
        Optional<User> result = userRepository.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
        verify(userRepository).findById(1L);
    }

    @Test
    void testFindByTenantId() {
        // Arrange
        User user2 = new User();
        user2.setId(2L);
        user2.setTenantId(10L);

        List<User> users = Arrays.asList(testUser, user2);
        when(userRepository.findByTenantId(10L)).thenReturn(users);
        when(userRepository.findByTenantId(999L)).thenReturn(Arrays.asList());

        // Act
        List<User> result1 = userRepository.findByTenantId(10L);
        List<User> result2 = userRepository.findByTenantId(999L);

        // Assert
        assertEquals(2, result1.size());
        assertTrue(result2.isEmpty());
    }

    @Test
    void testFindByUsername() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        // Act
        Optional<User> result1 = userRepository.findByUsername("testuser");
        Optional<User> result2 = userRepository.findByUsername("nonexistent");

        // Assert
        assertTrue(result1.isPresent());
        assertFalse(result2.isPresent());
    }

    @Test
    void testExistsByUsername() {
        // Arrange
        when(userRepository.existsByUsername("testuser")).thenReturn(true);
        when(userRepository.existsByUsername("nonexistent")).thenReturn(false);

        // Act
        boolean exists1 = userRepository.existsByUsername("testuser");
        boolean exists2 = userRepository.existsByUsername("nonexistent");

        // Assert
        assertTrue(exists1);
        assertFalse(exists2);
    }

    @Test
    void testFindByEmail() {
        // Arrange
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // Act
        Optional<User> result1 = userRepository.findByEmail("test@example.com");
        Optional<User> result2 = userRepository.findByEmail("nonexistent@example.com");

        // Assert
        assertTrue(result1.isPresent());
        assertFalse(result2.isPresent());
    }

    @Test
    void testFindByTenantIdAndUsername() {
        // Arrange
        when(userRepository.findByTenantIdAndUsername(10L, "testuser")).thenReturn(Optional.of(testUser));
        when(userRepository.findByTenantIdAndUsername(10L, "otheruser")).thenReturn(Optional.empty());

        // Act
        Optional<User> result1 = userRepository.findByTenantIdAndUsername(10L, "testuser");
        Optional<User> result2 = userRepository.findByTenantIdAndUsername(10L, "otheruser");

        // Assert
        assertTrue(result1.isPresent());
        assertFalse(result2.isPresent());
    }

    @Test
    void testFindByTenantIdAndStatus() {
        // Arrange
        User user2 = new User();
        user2.setId(2L);
        user2.setTenantId(10L);
        user2.setStatus("ACTIVE");

        List<User> activeUsers = Arrays.asList(testUser, user2);
        when(userRepository.findByTenantIdAndStatus(10L, "ACTIVE")).thenReturn(activeUsers);
        when(userRepository.findByTenantIdAndStatus(10L, "DISABLED")).thenReturn(Arrays.asList());

        // Act
        List<User> result1 = userRepository.findByTenantIdAndStatus(10L, "ACTIVE");
        List<User> result2 = userRepository.findByTenantIdAndStatus(10L, "DISABLED");

        // Assert
        assertEquals(2, result1.size());
        assertTrue(result2.isEmpty());
    }

    @Test
    void testUpdateLastLoginTime() {
        // Arrange
        doNothing().when(userRepository).updateLastLoginTime(1L, any(LocalDateTime.class));

        // Act
        userRepository.updateLastLoginTime(1L, LocalDateTime.now());

        // Assert
        verify(userRepository).updateLastLoginTime(eq(1L), any(LocalDateTime.class));
    }
}
