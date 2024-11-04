package com.vtb.seatransportservice.service.impl;
import com.vtb.seatransportservice.domain.dto.request.CategoryRequestDTO;
import com.vtb.seatransportservice.domain.dto.response.CategoryResponseDTO;
import com.vtb.seatransportservice.domain.entity.Category;
import com.vtb.seatransportservice.payload.ResponsePayload;
import com.vtb.seatransportservice.repository.ICategoryRepository;
import com.vtb.seatransportservice.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final ICategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    @Override
    public ResponsePayload getAllCategories() {
        List<Category> categories = categoryRepository.findAllByDeletedFalse();
        List<CategoryResponseDTO> categoryDTOs = categories.stream()
                .map(category -> modelMapper.map(category, CategoryResponseDTO.class))
                .collect(Collectors.toList());

        return ResponsePayload.builder()
                .data(categoryDTOs)
                .message("Categories retrieved successfully")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public ResponsePayload getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null && !category.getDeleted()) {
            CategoryResponseDTO categoryDTO = modelMapper.map(category, CategoryResponseDTO.class);
            return ResponsePayload.builder()
                    .data(categoryDTO)
                    .message("Category retrieved successfully")
                    .status(HttpStatus.OK)
                    .build();
        } else {
            return ResponsePayload.builder()
                    .data(null)
                    .message("Category not found")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @Override
    public ResponsePayload saveCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = modelMapper.map(categoryRequestDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        CategoryResponseDTO savedCategoryDTO = modelMapper.map(savedCategory, CategoryResponseDTO.class);

        return ResponsePayload.builder()
                .data(savedCategoryDTO)
                .message("Category saved successfully")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public ResponsePayload updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null && !category.getDeleted()) {
            category.setName(categoryRequestDTO.getName());
            category.setUrl_name(categoryRequestDTO.getUrl_name());
            Category updatedCategory = categoryRepository.save(category);
            CategoryResponseDTO updatedCategoryDTO = modelMapper.map(updatedCategory, CategoryResponseDTO.class);

            return ResponsePayload.builder()
                    .data(updatedCategoryDTO)
                    .message("Category updated successfully")
                    .status(HttpStatus.OK)
                    .build();
        } else {
            return ResponsePayload.builder()
                    .data(null)
                    .message("Category not found")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @Override
    public ResponsePayload deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null && !category.getDeleted()) {
            category.setDeleted(true);
            categoryRepository.save(category);
            return ResponsePayload.builder()
                    .data(null)
                    .message("Category deleted successfully")
                    .status(HttpStatus.OK)
                    .build();
        } else {
            return ResponsePayload.builder()
                    .data(null)
                    .message("Category not found")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @Override
    public void deleteCategoriesByIds(List<Long> ids) {
        List<Category> categories = categoryRepository.findAllById(ids);
        for (Category category : categories) {
            category.setDeleted(true);
        }
        categoryRepository.saveAll(categories);
    }
}




