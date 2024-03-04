package com.facugl.ecommerce.server.infrastructure.adapter.input.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facugl.ecommerce.server.application.mapper.ApplicationCategoryMapper;
import com.facugl.ecommerce.server.application.mapper.ApplicationProductMapper;
import com.facugl.ecommerce.server.application.port.input.categories.ActiveCategoryUseCase;
import com.facugl.ecommerce.server.application.port.input.categories.CreateCategoryUseCase;
import com.facugl.ecommerce.server.application.port.input.categories.GetAllCategoriesUseCase;
import com.facugl.ecommerce.server.application.port.input.categories.GetAllMainCategoriesUseCase;
import com.facugl.ecommerce.server.application.port.input.categories.GetAllProductsByCategoryUseCase;
import com.facugl.ecommerce.server.application.port.input.categories.GetAllSubCategoriesUseCase;
import com.facugl.ecommerce.server.application.port.input.categories.GetCategoryUseCase;
import com.facugl.ecommerce.server.application.port.input.categories.UpdateCategoryUseCase;
import com.facugl.ecommerce.server.application.port.output.CategoryOutputPort;
import com.facugl.ecommerce.server.common.WebAdapter;
import com.facugl.ecommerce.server.domain.model.categories.Category;
import com.facugl.ecommerce.server.domain.model.categories.CategoryStatus;
import com.facugl.ecommerce.server.domain.model.products.Product;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.CategoryRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.CategoryResponse;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.ProductResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@WebAdapter
@RestController
@RequestMapping("/categories")
public class CategoryRestAdapter {

        private final ApplicationCategoryMapper categoryMapper;
        private final ApplicationProductMapper productMapper;

        private final CreateCategoryUseCase createCategoryUseCase;
        private final GetCategoryUseCase getCategoryUseCase;
        private final GetAllCategoriesUseCase getAllCategoriesUseCase;
        private final GetAllMainCategoriesUseCase getAllMainCategoriesUseCase;
        private final GetAllSubCategoriesUseCase getAllSubCategoriesUseCase;
        private final UpdateCategoryUseCase updateCategoryUseCase;
        private final ActiveCategoryUseCase activeCategoryUseCase;
        private final GetAllProductsByCategoryUseCase getAllProductsByCategoryUseCase;

        private final CategoryOutputPort categoryOutputPort;

        @PostMapping
        public ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest categoryToCreate) {
                Category category = categoryMapper.mapToCategory(categoryToCreate, categoryOutputPort);

                Category createdCategory = createCategoryUseCase.createCategory(category);

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(categoryMapper.mapToCategoryResponse(createdCategory));
        }

        @GetMapping
        public ResponseEntity<List<CategoryResponse>> getAllCategories() {
                List<CategoryResponse> categories = getAllCategoriesUseCase.getAllCategories()
                                .stream()
                                .map(categoryMapper::mapToCategoryResponse)
                                .collect(Collectors.toList());

                return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(categories);
        }

        @GetMapping("/{id}")
        public ResponseEntity<CategoryResponse> getOneCategory(@PathVariable Long id) {
                Category category = getCategoryUseCase.getCategoryById(id);

                return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(categoryMapper.mapToCategoryResponse(category));
        }

        @GetMapping("/main")
        public ResponseEntity<List<CategoryResponse>> getAllMainCategories() {
                List<CategoryResponse> mainCategories = getAllMainCategoriesUseCase.getAllMainCategories()
                                .stream()
                                .map(categoryMapper::mapToCategoryResponse)
                                .collect(Collectors.toList());

                return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(mainCategories);
        }

        @GetMapping("/main/{parentId}/subcategories")
        public ResponseEntity<List<CategoryResponse>> getSubcategories(@PathVariable Long parentId) {
                List<CategoryResponse> subCategories = getAllSubCategoriesUseCase.getAllSubCategories(parentId)
                                .stream()
                                .map(categoryMapper::mapToCategoryResponse)
                                .collect(Collectors.toList());

                return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(subCategories);
        }

        @PutMapping("/{id}")
        public ResponseEntity<CategoryResponse> updateCategory(
                        @PathVariable Long id,
                        @RequestBody @Valid CategoryRequest categoryToUpdate) {
                Category category = categoryMapper.mapToCategory(categoryToUpdate, categoryOutputPort);

                Category updatedCategory = updateCategoryUseCase.updateCategory(id, category);

                return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(categoryMapper.mapToCategoryResponse(updatedCategory));
        }

        @PatchMapping("/{id}/active")
        public ResponseEntity<Void> activeCategory(@PathVariable Long id, @RequestBody CategoryStatus status) {
                activeCategoryUseCase.activeCategory(id, status);

                return ResponseEntity
                                .status(HttpStatus.OK)
                                .build();
        }

        @GetMapping("/{id}/products")
        public ResponseEntity<List<ProductResponse>> getAllProducts(@PathVariable Long id) {
                List<Product> productList = getAllProductsByCategoryUseCase.getAllProducts(id);

                List<ProductResponse> productResponseList = productList.stream()
                                .map(productMapper::mapToProductResponse)
                                .collect(Collectors.toList());

                return ResponseEntity
                                .status(HttpStatus.OK)
                                .body(productResponseList);
        }

}
