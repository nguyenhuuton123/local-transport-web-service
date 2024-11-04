package com.vtb.seatransportservice.repository;

import com.vtb.seatransportservice.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u where u.username = :username")
    Optional<User> findByUsername(String username);
    @Query("SELECT u FROM User u JOIN FETCH u.role WHERE u.isDelete = false")
    Page<User> findAllUsers(Pageable pageable);
    Optional<User> findUserById(Long id);
    boolean existsByUsername(String username);
}
