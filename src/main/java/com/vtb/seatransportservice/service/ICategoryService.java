package com.vtb.seatransportservice.service;

import com.vtb.seatransportservice.domain.dto.request.CategoryRequestDTO;
import com.vtb.seatransportservice.payload.ResponsePayload;

import java.util.List;

public interface ICategoryService {
    ResponsePayload getAllCategories();
    ResponsePayload getCategoryById(Long id);
    ResponsePayload saveCategory(CategoryRequestDTO categoryRequestDTO);
    ResponsePayload updateCategory(Long id, CategoryRequestDTO categoryRequestDTO);
    ResponsePayload deleteCategory(Long id);
    void deleteCategoriesByIds(List<Long> ids);
}


