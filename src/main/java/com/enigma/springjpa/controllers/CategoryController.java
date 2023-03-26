package com.enigma.springjpa.controllers;

import com.enigma.springjpa.models.dto.CategoryData;
import com.enigma.springjpa.models.dto.ResponseData;
import com.enigma.springjpa.models.dto.SearchData;
import com.enigma.springjpa.models.enitity.Category;
import com.enigma.springjpa.services.CategoryService;
import com.fasterxml.jackson.annotation.OptBoolean;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity createCategory(@Valid @RequestBody CategoryData categoryData, Errors errors){
        ResponseData<Category> responseData = new ResponseData<>();
        if(errors.hasErrors()){
            for(ObjectError err : errors.getAllErrors()){
                responseData.getMessage().add(err.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        Category category1 = modelMapper.map(categoryData,Category.class);
        responseData.setStatus(true);
        responseData.setPayload(categoryService.save(category1));
        return ResponseEntity.ok(responseData);
    }
    @GetMapping
    public ResponseEntity getAll(){
        Iterable<Category> categories = categoryService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        Category categoryOptional = categoryService.findById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryOptional);
    }
    @PutMapping("/{id}")
    public ResponseEntity update(@Valid @PathVariable Long id, @RequestBody CategoryData categoryData, Errors errors){
        ResponseData<Category> responseData = new ResponseData<>();
        if(errors.hasErrors()){
            for(ObjectError err : errors.getAllErrors()){
                responseData.getMessage().add(err.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        Category newCategory = modelMapper.map(categoryData, Category.class);
        responseData.setStatus(true);
        responseData.setPayload(categoryService.update(newCategory,id));
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        categoryService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Category with id " + id + " deleted successfully");
    }

    @PostMapping("/search/{size}/{page}/{sort}")
    public Iterable<Category> findByName(@RequestBody SearchData searchData, @PathVariable int size, @PathVariable int page, @PathVariable String sort){
        Pageable pageable= PageRequest.of(page,size, Sort.by("id").ascending());
        if(sort.equalsIgnoreCase("desc")){
            pageable= PageRequest.of(page,size, Sort.by("id").descending());
        }
        return categoryService.findByNameContains(searchData.getSearchKey(),pageable);
    }

}
