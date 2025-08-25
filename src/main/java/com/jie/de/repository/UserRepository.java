package com.jie.de.repository;



import com.jie.de.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(Long userId);

    void deleteById(Long userId);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByUserId(Long userId);
}
