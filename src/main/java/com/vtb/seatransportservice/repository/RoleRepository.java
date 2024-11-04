package com.vtb.seatransportservice.repository;

import com.vtb.seatransportservice.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM User u JOIN u.role r WHERE u.username = :username")
    Role findRolesByUsername(String username);
    Optional<Role> findByName(String roleName);
}
