package sla.ef_07_05.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import sla.ef_07_05.Exception.ResourceNotFoundException;
import sla.ef_07_05.model.User;
import sla.ef_07_05.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password");
    }

    @Test
    public void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));



        List<User> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(user.getEmail(), users.get(0).getEmail());
    }

    @Test
    public void testGetUserById_UserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserById(1L);

        assertTrue(foundUser.isPresent());
        assertEquals(user.getEmail(), foundUser.get().getEmail());
    }

    @Test
    public void testGetUserById_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.getUserById(1L);

        assertFalse(foundUser.isPresent());
    }

    @Test
    public void testRegisterUser() {
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        User registeredUser = userService.registerUser(user);

        assertNotNull(registeredUser);
        assertEquals("encodedPassword", registeredUser.getPassword());
        verify(userRepository).save(registeredUser);
    }

    @Test
    public void testUpdateUser_UserExists() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userService.updateUser(1L, user);

        assertNotNull(updatedUser);
        assertEquals(user.getId(), updatedUser.getId());
        verify(userRepository).save(updatedUser);
    }

    @Test
    public void testUpdateUser_UserNotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                userService.updateUser(1L, user)
        );

        assertEquals("User not found with id 1", exception.getMessage());
    }

    @Test
    public void testDeleteUser_UserExists() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    public void testDeleteUser_UserNotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                userService.deleteUser(1L)
        );

        assertEquals("User not found with id 1", exception.getMessage());
    }
}
