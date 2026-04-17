package com.enotes.controller;

import com.enotes.repository.CategoryRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.CategoryResponse;
import com.enotes.entity.Category;
import com.enotes.service.CategoryService;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

	private final CategoryRepository categoryRepository;
	@Autowired
	private CategoryService categoryService;

	CategoryController(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	@PostMapping("/save-category")
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto)
	{
		Boolean saveCategory = categoryService.saveCategory(categoryDto);
		
		if(saveCategory)
		{
			return new ResponseEntity<>("Data Saved Successfully",HttpStatus.CREATED);
		}else
		{
			return new ResponseEntity<>("Data Not Save",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getAllCategory")
	public ResponseEntity<?> getAllCategory()
	{
		List<CategoryDto> allCategory = categoryService.getAllCategory();
		
		if(CollectionUtils.isEmpty(allCategory))
		{
			return ResponseEntity.noContent().build();
		}else
		{
			return new ResponseEntity<>(allCategory,HttpStatus.OK);
		}
	}
	
	@GetMapping("/activeCategory")
	public ResponseEntity<?> getActiveCategory()
	{
	List<CategoryResponse> getActiveCategory=categoryService.getActiveCategory();
	
	if(CollectionUtils.isEmpty(getActiveCategory))
	{
		return ResponseEntity.noContent().build();
	}else
		
	{
	return new ResponseEntity<>(getActiveCategory,HttpStatus.OK);	
	}
	
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id)
	{
		CategoryDto categoryDto=categoryService.getCategoryById(id);
		if(ObjectUtils.isEmpty(categoryDto))
		{
			return new ResponseEntity<>("Category Not Found With Id "+id,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(categoryDto,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id)
	{
		Boolean deleted=categoryService.deleteCategory(id);
		if(deleted)
		{
			return new ResponseEntity<>("Category Deleted Successfully",HttpStatus.OK);
		}
		return new ResponseEntity<>("Category Not Deleted",HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
}
