package com.LoginRegisterAuth.Repository;

import com.LoginRegisterAuth.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface userRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
