package com.vtb.seatransportservice.repository;
import com.vtb.seatransportservice.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByDeletedFalse();
}