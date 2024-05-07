package com.facugl.ecommerce.server.infrastructure.adapter.input.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import com.facugl.ecommerce.server.application.mapper.ApplicationVariantMapper;
import com.facugl.ecommerce.server.application.port.input.categories.ActiveCategoryUseCase;
import com.facugl.ecommerce.server.application.port.input.categories.CreateCategoryUseCase;
import com.facugl.ecommerce.server.application.port.input.categories.GetAllCategoriesUseCase;
import com.facugl.ecommerce.server.application.port.input.categories.GetAllMainCategoriesUseCase;
import com.facugl.ecommerce.server.application.port.input.categories.GetAllProductsByCategoryUseCase;
import com.facugl.ecommerce.server.application.port.input.categories.GetAllSubCategoriesUseCase;
import com.facugl.ecommerce.server.application.port.input.categories.GetAllVariantsByCategoryUseCase;
import com.facugl.ecommerce.server.application.port.input.categories.GetCategoryUseCase;
import com.facugl.ecommerce.server.application.port.input.categories.UpdateCategoryUseCase;
import com.facugl.ecommerce.server.application.service.CategoryService;
import com.facugl.ecommerce.server.common.WebAdapter;
import com.facugl.ecommerce.server.domain.model.categories.Category;
import com.facugl.ecommerce.server.domain.model.categories.CategoryStatus;
import com.facugl.ecommerce.server.domain.model.products.Product;
import com.facugl.ecommerce.server.domain.model.variants.Variant;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.request.CategoryRequest;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.CategoryResponse;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.ProductResponse;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.data.response.VariantResponse;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.categories.CreateCategoryValidationGroup;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.validation.groups.categories.UpdateCategoryValidationGroup;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@WebAdapter
@RestController
@RequestMapping("/categories")
public class CategoryRestAdapter {

	private final ApplicationCategoryMapper categoryMapper;
	private final ApplicationProductMapper productMapper;
	private final ApplicationVariantMapper variantMapper;

	private final CreateCategoryUseCase createCategoryUseCase;
	private final GetCategoryUseCase getCategoryUseCase;
	private final GetAllCategoriesUseCase getAllCategoriesUseCase;
	private final GetAllMainCategoriesUseCase getAllMainCategoriesUseCase;
	private final GetAllSubCategoriesUseCase getAllSubCategoriesUseCase;
	private final UpdateCategoryUseCase updateCategoryUseCase;
	private final ActiveCategoryUseCase activeCategoryUseCase;
	private final GetAllProductsByCategoryUseCase getAllProductsByCategoryUseCase;
	private final GetAllVariantsByCategoryUseCase getAllVariantsByCategoryUseCase;

	private final CategoryService categoryService;

	@PostMapping
	public ResponseEntity<CategoryResponse> createCategory(
			@RequestBody @Validated(CreateCategoryValidationGroup.class) CategoryRequest categoryToCreate) {
		Category category = categoryService.mapCategoryRequestToCategory(categoryToCreate);

		Category createdCategory = createCategoryUseCase.createCategory(category);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(categoryMapper.mapCategoryToCategoryResponse(createdCategory));
	}

	@GetMapping
	public ResponseEntity<List<CategoryResponse>> getAllCategories() {
		List<CategoryResponse> categories = getAllCategoriesUseCase.getAllCategories()
				.stream()
				.map(categoryMapper::mapCategoryToCategoryResponse)
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
				.body(categoryMapper.mapCategoryToCategoryResponse(category));
	}

	@GetMapping("/main")
	public ResponseEntity<List<CategoryResponse>> getAllMainCategories() {
		List<CategoryResponse> mainCategories = getAllMainCategoriesUseCase.getAllMainCategories()
				.stream()
				.map(categoryMapper::mapCategoryToCategoryResponse)
				.collect(Collectors.toList());

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(mainCategories);
	}

	@GetMapping("/main/{id}/subcategories")
	public ResponseEntity<List<CategoryResponse>> getSubcategories(@PathVariable Long id) {
		List<CategoryResponse> subCategories = getAllSubCategoriesUseCase.getAllSubCategories(id)
				.stream()
				.map(categoryMapper::mapCategoryToCategoryResponse)
				.collect(Collectors.toList());

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(subCategories);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CategoryResponse> updateCategory(
			@PathVariable Long id,
			@RequestBody @Validated(UpdateCategoryValidationGroup.class) CategoryRequest categoryToUpdate) {
		Category category = categoryService.mapCategoryRequestToCategory(categoryToUpdate);

		Category updatedCategory = updateCategoryUseCase.updateCategory(id, category);

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(categoryMapper.mapCategoryToCategoryResponse(updatedCategory));
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
		List<Product> productList = getAllProductsByCategoryUseCase.getAllProductsByCategory(id);

		List<ProductResponse> productResponseList = productList
				.stream()
				.map(productMapper::mapProductToProductResponse)
				.collect(Collectors.toList());

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(productResponseList);
	}

	@GetMapping("/{id}/variants")
	public ResponseEntity<List<VariantResponse>> getAllVariants(@PathVariable Long id) {
		List<Variant> variantList = getAllVariantsByCategoryUseCase.getAllVariantsByCategory(id);

		List<VariantResponse> variantResponseList = variantList
				.stream()
				.map(variantMapper::mapVariantToVariantResponse)
				.collect(Collectors.toList());

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(variantResponseList);
	}

}
