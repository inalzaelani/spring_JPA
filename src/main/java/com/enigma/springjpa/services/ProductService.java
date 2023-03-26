package com.enigma.springjpa.services;

import com.enigma.springjpa.Repository.ProductRepo;
import com.enigma.springjpa.models.enitity.Product;
import com.enigma.springjpa.models.enitity.Supplier;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private ProductRepo productRepo;

    public Product save(Product product) {
        return productRepo.save(product);
    }

    public Iterable<Product> findAll() {
        return productRepo.findAll();
    }

    public Product findBy(Long id) {

        Optional<Product> product = productRepo.findById(id);
        return product.get();
    }

    public Product update(Product product, Long id) {
        Optional<Product> find = productRepo.findById(id);
        if (find.isPresent()) {
            Product existingProduct = find.get();
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            productRepo.save(existingProduct);
            return existingProduct;
        } else {
            throw new EntityNotFoundException("Cannot find product with ID " + id);
        }
    }
    public void deleteById(Long id) {
        Optional<Product> productOptional = productRepo.findById(id);
        if (productOptional.isPresent()) {
            productRepo.deleteById(id);
        } else {
            throw new RuntimeException("Product not found with id " + id);
        }
    }

    public void addSupplier(Supplier supplier, Long productId){
        Product product = findBy(productId);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        product.getSupplier().add(supplier);
        save(product);
    }
    public List<Product> findByNameService(String name){
        return productRepo.findByName('%'+name+'%');
    }

    public List<Product> findByCategoryID(Long categoryId){
        return productRepo.findByCategoryId(categoryId);
    }

    public List<Product> findBySupplier(Long supplierId){
        Supplier supplier = supplierService.findById(supplierId);
        if(supplier==null){
            throw new RuntimeException("Supplier not found");
        }
        return productRepo.findBySupplier(supplier);
    }

}