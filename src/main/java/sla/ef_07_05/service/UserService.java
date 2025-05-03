package sla.ef_07_05.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> registerUser(User user) {
        // Логика регистрации (шифрование пароля и т.д.)
        return Optional.of(userRepository.save(user));
    }

    public Optional<User> authenticateUser(String email, String password) {
        // Логика аутентификации
    }
}

