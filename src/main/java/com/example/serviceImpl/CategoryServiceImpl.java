package com.example.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.dto.CategoryDto;
import com.example.dto.CategoryResponse;
import com.example.entity.Category;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.ValidationException;
import com.example.repository.CategoryRepository;
import com.example.service.CategoryService;
import com.example.util.Validation;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private Validation validation;

	// <<======= create category =======>>
	@Override
	public Boolean saveCategory(CategoryDto categoryDto) {

		// Validation checking

		validation.categoryValidation(categoryDto);
		Category category = mapper.map(categoryDto, Category.class);

		if (ObjectUtils.isEmpty(category.getId())) {
			category.setIsDeleted(false);
//			category.setCreatedBy(2);
			category.setCreatedOn(new Date());
		} else {
			updateCategory(category);
		}
		Category saveCategory = categoryRepo.save(category);
		if (ObjectUtils.isEmpty(saveCategory)) {
			return false;
		}
		return true;
	}

	private void updateCategory(Category category) {
		Optional<Category> findById = categoryRepo.findById(category.getId());
		if (findById.isPresent()) {
			Category existingCategory = findById.get();
			category.setCreatedBy(existingCategory.getCreatedBy());
			category.setCreatedOn(existingCategory.getCreatedOn());
			category.setIsDeleted(existingCategory.getIsDeleted());

//			category.setUpdatedBy(1);
//			category.setUpdatedOn(new Date());
		}

	}

	// <========get all category========>
	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categories = categoryRepo.findByIsDeletedFalse();

		List<CategoryDto> categoryDtoList = categories.stream().map(cat -> mapper.map(cat, CategoryDto.class)).toList();
		return categoryDtoList;
	}

	// <-------- get only active category -------->

	@Override
	public List<CategoryResponse> getActiveCategory() {

		List<Category> categories = categoryRepo.findByIsActiveTrueAndIsDeletedFalse();
		List<CategoryResponse> categoryList = categories.stream().map(cat -> mapper.map(cat, CategoryResponse.class))
				.toList();
		return categoryList;
	}

	// <======= get category by id =======>

	@Override
	public CategoryDto getCategoryById(Integer id) throws Exception {
		Category category = categoryRepo.findByIdAndIsDeletedFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id =" + id));
		if (!ObjectUtils.isEmpty(category)) {
			// if (category.getName() == null) {

			// throw new IllegalArgumentException("name is null");

			return mapper.map(category, CategoryDto.class);
		}
		return null;
	}

	// <======= delete category by id =======>

	@Override
	public Boolean deleteCategory(Integer id) {
		Optional<Category> findByCategory = categoryRepo.findById(id);
		if (findByCategory.isPresent()) {
			Category category = findByCategory.get();
			category.setIsDeleted(true);
			categoryRepo.save(category);
			return true;
		}
		return false;
	}

}
