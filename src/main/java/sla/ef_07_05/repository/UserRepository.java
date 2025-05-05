package sla.ef_07_05.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sla.ef_07_05.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String username);
}


