package com.vtb.seatransportservice.repository;

import com.vtb.seatransportservice.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IPostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.name LIKE %:name%")
    List<Post> searchPostsByName(@Param("name") String name);
    Optional<Post> findById(Long id);
}
