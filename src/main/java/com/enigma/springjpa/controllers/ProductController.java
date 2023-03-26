package com.enigma.springjpa.controllers;

import com.enigma.springjpa.models.dto.ResponseData;
import com.enigma.springjpa.models.dto.SearchData;
import com.enigma.springjpa.models.enitity.Product;
import com.enigma.springjpa.models.enitity.Supplier;
import com.enigma.springjpa.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity createProduct(@Valid @RequestBody Product product, Errors errors){
        ResponseData<Product> responseData = new ResponseData<>();
        if(errors.hasErrors()){
            for(ObjectError err : errors.getAllErrors()){
                responseData.getMessage().add(err.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        responseData.setPayload(productService.save(product));
        return ResponseEntity.ok(responseData);
    }
    @GetMapping
    public ResponseEntity getAll(){
        Iterable<Product> products = productService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        Product productOptional = productService.findBy(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(productOptional);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@Valid @PathVariable Long id, @RequestBody Product product, Errors errors){
        ResponseData<Product> responseData = new ResponseData<>();
        if(errors.hasErrors()){
            for(ObjectError err : errors.getAllErrors()){
                responseData.getMessage().add(err.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        Product newProduct = productService.update(product,id);
        responseData.setStatus(true);
        responseData.setPayload(newProduct);
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        productService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Product with id " + id + " deleted successfully");
    }

    @PostMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public void addSupplier(@RequestBody Supplier supplier, @PathVariable("id") Long productId) {
        productService.addSupplier(supplier, productId);
    }

    @PostMapping("/search/name")
    public List<Product> findByNameController(@RequestBody SearchData searchData){
        return productService.findByNameService(searchData.getSearchKey());
    }
    @GetMapping("/search/category/{categoryId}")
    public List<Product> findByCategoryIdController(@PathVariable Long categoryId){
        return productService.findByCategoryID(categoryId);
    }
    @GetMapping("/search/supplier/{supplierId}")
    public List<Product> findBySupplierController(@PathVariable Long supplierId){
        return productService.findBySupplier(supplierId);
    }
}
