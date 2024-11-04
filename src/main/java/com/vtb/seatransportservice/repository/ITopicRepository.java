package com.vtb.seatransportservice.repository;

import com.vtb.seatransportservice.domain.entity.Category;
import com.vtb.seatransportservice.domain.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITopicRepository extends JpaRepository<Topic, Long> {

    List<Topic> findAllByCategoryId(Long categoryId);

    List<Topic> findAllByDeletedFalseAndCategoryId(Long categoryId);

    boolean existsByNameAndCategoryId(String name, Long categoryId);

    boolean existsByNameAndCategoryIdAndIdNot(String name, Long categoryId, Long id);

    boolean existsByDeletedFalseAndNameAndCategoryId(@Param("name") String name, @Param("categoryId") Long id);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Topic t WHERE t.url_name = :url_name AND t.deleted = FALSE and t.category.id = :categoryId")
    boolean existsByDeletedFalseAndUrlNameAndCategoryId(@Param("url_name") String url_name, @Param("categoryId") Long id);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Topic t WHERE t.name = :name AND t.category.id = :categoryId AND t.deleted = false AND t.id <> :id")
    boolean existsByDeletedFalseAndNameExceptIdAndCategoryId(@Param("name") String name, @Param("id") Long id, @Param("categoryId") Long categoryId);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Topic t WHERE t.url_name = :url_name AND t.category.id = :categoryId AND t.deleted = false AND t.id <> :id")
    boolean existsByDeletedFalseAndUrlNameExceptIdAndCategoryId(@Param("url_name") String url_name, @Param("id") Long id, @Param("categoryId") Long categoryId);

}
