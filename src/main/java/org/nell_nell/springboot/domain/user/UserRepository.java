package org.nell_nell.springboot.domain.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findById(String id);

    List<User> findByEmailContaining(String email, Pageable pageable);

    List<User> findByNameContaining(String name, Pageable pageable);
}