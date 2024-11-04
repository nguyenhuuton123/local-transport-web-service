package com.vtb.seatransportservice.controller.admin;

import com.vtb.seatransportservice.domain.dto.request.CategoryRequestDTO;
import com.vtb.seatransportservice.payload.ResponsePayload;
import com.vtb.seatransportservice.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<ResponsePayload> getAllCategories() {
        ResponsePayload responsePayload = categoryService.getAllCategories();
        return ResponseEntity.status(responsePayload.getStatus()).body(responsePayload);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePayload> getCategoryById(@PathVariable Long id) {
        ResponsePayload responsePayload = categoryService.getCategoryById(id);
        return ResponseEntity.status(responsePayload.getStatus()).body(responsePayload);
    }

    @PostMapping
    public ResponseEntity<ResponsePayload> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO) {
        ResponsePayload responsePayload = categoryService.saveCategory(categoryRequestDTO);
        return ResponseEntity.status(responsePayload.getStatus()).body(responsePayload);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload> updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDTO categoryRequestDTO) {
        ResponsePayload responsePayload = categoryService.updateCategory(id, categoryRequestDTO);
        return ResponseEntity.status(responsePayload.getStatus()).body(responsePayload);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponsePayload> deleteCategory(@PathVariable Long id) {
        ResponsePayload responsePayload = categoryService.deleteCategory(id);
        return ResponseEntity.status(responsePayload.getStatus()).body(responsePayload);
    }

    List<Long> listCategoryId;

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteCategories(@RequestBody List<CategoryRequestDTO> deleteCategoryRequest) {
        listCategoryId = new ArrayList<>();
        for (CategoryRequestDTO i: deleteCategoryRequest) {
            listCategoryId.add(i.getId());
        }
        categoryService.deleteCategoriesByIds(listCategoryId);
        return ResponseEntity.ok().build();
    }
}


