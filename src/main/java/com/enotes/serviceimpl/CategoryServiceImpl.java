package com.enotes.serviceimpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.entity.Category;
import com.enotes.repository.CategoryRepository;
import com.enotes.service.CategoryService;

@Service
public class CategoryServiceImpl  implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Override
	public Boolean saveCategory(Category category) {
		// TODO Auto-generated method stub
		
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
	public List<Category> getAllCategory() {
		// TODO Auto-generated method stub
		List<Category> allCategory = categoryRepo.findAll();
		return  allCategory;
	}

}
