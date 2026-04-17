package com.enotes.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.CategoryResponse;
import com.enotes.entity.Category;
import com.enotes.repository.CategoryRepository;
import com.enotes.service.CategoryService;

@Service
public class CategoryServiceImpl  implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public Boolean saveCategory(CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		
//		Category category=new Category();
//		category.setName(categoryDto.getName());
//		category.setDescription(categoryDto.getDescription());
//		category.setIsActive(categoryDto.getIsActive());
		
		Category category = mapper.map(categoryDto, Category.class);
		
		 category.setCreatedBy(1);
		 category.setCreatedOn(new Date());
		 category.setIsDeleted(false);
		  
		  Category saveCategory = categoryRepo.save(category);
		 
		 if(ObjectUtils.isEmpty(saveCategory)) 
		 {
			 return false;
			 }
		 		
		
		return true;
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		// TODO Auto-generated method stub
		List<Category> allCategory = categoryRepo.findByIsDeletedFalse();
		List<CategoryDto> categoryDtoList = allCategory.stream().map(cat->mapper.map(cat, CategoryDto.class)).toList();
		
		return  categoryDtoList;
	}

	@Override
	public List<CategoryResponse> getActiveCategory() {
		// TODO Auto-generated method stub
		List<Category> categories=categoryRepo.findByIsActiveTrueAndIsDeletedFalse();
		List<CategoryResponse> categoryList = categories.stream().map(cat ->mapper.map(cat,CategoryResponse.class)).toList();
		
		return categoryList ;
	}

	@Override
	public CategoryDto getCategoryById(Integer id) {
		// TODO Auto-generated method stub
		Optional<Category> findByCategory = categoryRepo.findByIdAndIsDeletedFalse(id);
		if(findByCategory.isPresent())
		{
			Category category = findByCategory.get();
			return mapper.map(category, CategoryDto.class);
		}
		return null;
	}

	@Override
	public Boolean deleteCategory(Integer id) {
		// TODO Auto-generated method stub
		Optional<Category> findByCategory = categoryRepo.findById(id);
		
		if(findByCategory.isPresent())
		{
			Category category=findByCategory.get();
			category.setIsDeleted(true);
			categoryRepo.save(category);
			return true;
		}
		return false;
	}

}
