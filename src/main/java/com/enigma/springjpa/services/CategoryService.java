package com.enigma.springjpa.services;

import com.enigma.springjpa.Repository.CategoryRepo;
import com.enigma.springjpa.models.enitity.Category;
import com.enigma.springjpa.models.enitity.Product;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public Iterable<Category> findAll(){
        return categoryRepo.findAll();
    }

    public Category findById(Long id){
        Optional<Category> category = categoryRepo.findById(id);
        if(category==null){
            return null;
        }
        return category.get();
    }

    public Category save(Category category){
        return categoryRepo.save(category);
    }

    public Category update(Category category, Long id){
        Optional<Category> find= categoryRepo.findById(id);
        if (find.isPresent()) {
            Category existingCategories = find.get();
            existingCategories.setName(category.getName());
            categoryRepo.save(existingCategories);
            return existingCategories;
        } else {
            throw new EntityNotFoundException("Cannot find catergory with ID " + id);
        }
    }
    public void deleteById(Long id){
        Optional<Category> categoryOptional = categoryRepo.findById(id);
        if(categoryOptional.isPresent()){
            categoryRepo.deleteById(id);
        }
        else throw new RuntimeException("Category not found with id " + id);
    }
    public Iterable<Category> findByNameContains( String name, Pageable pageable){
        return categoryRepo.findByNameContainsIgnoreCase(name,pageable);
    }
}

