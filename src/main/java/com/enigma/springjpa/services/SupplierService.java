package com.enigma.springjpa.services;

import com.enigma.springjpa.Repository.SupplierRepo;
import com.enigma.springjpa.models.enitity.Supplier;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SupplierService {
    @Autowired
    private SupplierRepo supplierRepo;

    public Iterable<Supplier> findAll() {
        return supplierRepo.findAll();
    }

    public Supplier findById(Long id) {
        Optional<Supplier> supplier = supplierRepo.findById(id);
        if (supplier == null) {
            return null;
        }
        return supplier.get();
    }

    public Supplier save(Supplier supplier) {
        return supplierRepo.save(supplier);
    }

    public Supplier update(Supplier supplier, Long id) {
        Optional<Supplier> find = supplierRepo.findById(id);
        if (find.isPresent()) {
            Supplier existingSupplier = find.get();
            existingSupplier.setName(supplier.getName());
            existingSupplier.setAddress(supplier.getAddress());
            existingSupplier.setEmail(supplier.getEmail());
            existingSupplier.setPhone(supplier.getPhone());
            supplierRepo.save(existingSupplier);
            return existingSupplier;
        } else {
            throw new EntityNotFoundException("Cannot find supplier with ID " + id);
        }
    }
    public void deleteById(Long id){
        Optional<Supplier> supplierOptional = supplierRepo.findById(id);
        if(supplierOptional.isPresent()){
            supplierRepo.deleteById(id);
        }
        else{
            throw new RuntimeException("Category not found with id "+ id);
        }
    }

    public Supplier findByEmail(String email){
        return supplierRepo.findByEmail(email);
    }

    public List<Supplier> findByNameContains(String name){
        return supplierRepo.findByNameContains(name);
    }
    public List<Supplier> findByNameContainsOrEmailContain(String name, String email){
        return supplierRepo.findByNameContainsIgnoreCaseOrEmailContainsIgnoreCase(name,email);
    }

}

