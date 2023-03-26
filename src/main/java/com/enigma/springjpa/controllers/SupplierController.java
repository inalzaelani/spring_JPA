package com.enigma.springjpa.controllers;

import com.enigma.springjpa.models.dto.ResponseData;
import com.enigma.springjpa.models.dto.SearchData;
import com.enigma.springjpa.models.dto.SupplierData;
import com.enigma.springjpa.models.enitity.Supplier;
import com.enigma.springjpa.services.SupplierService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SupplierService supplierService;
    @PostMapping
    public ResponseEntity createSupplier(@Valid @RequestBody SupplierData supplierData, Errors errors){
        ResponseData<Supplier> responseData = new ResponseData<>();
        if(errors.hasErrors()){
            for(ObjectError err : errors.getAllErrors()){
                responseData.getMessage().add(err.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        Supplier supplier = modelMapper.map(supplierData,Supplier.class);
        responseData.setStatus(true);
        responseData.setPayload(supplierService.save(supplier));
        return ResponseEntity.ok(responseData);
    }
    @GetMapping
    public ResponseEntity getAll(){
        Iterable<Supplier> suppliers = supplierService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(suppliers);
    }
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        Supplier supplier = supplierService.findById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(supplier);
    }
    @PutMapping("/{id}")
    public ResponseEntity update(@Valid @PathVariable Long id, @RequestBody SupplierData supplierData, Errors errors){
        ResponseData<Supplier> responseData = new ResponseData<>();
        if(errors.hasErrors()){
            for(ObjectError err : errors.getAllErrors()){
                responseData.getMessage().add(err.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        Supplier supplier = modelMapper.map(supplierData, Supplier.class);
        responseData.setStatus(true);
        responseData.setPayload(supplierService.update(supplier,id));
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        supplierService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Supplier with id " + id + " deleted successfully");
    }

    @PostMapping("/search/email")
    public Supplier findByEmail(@RequestBody SearchData searchData){
        return supplierService.findByEmail(searchData.getSearchKey());
    }
    @PostMapping("/search/name")
    public List<Supplier> findByNameContains(@RequestBody SearchData searchData){
        return supplierService.findByNameContains(searchData.getSearchKey());
    }

    @PostMapping("search/nameoremail")
    public ResponseEntity findByNameOrEmail(@RequestBody SearchData searchData){
        List<Supplier> suppliers = supplierService.findByNameContainsOrEmailContain(searchData.getSearchKey(),searchData.getOtherSearchKey());
        return ResponseEntity.status(HttpStatus.OK).body(suppliers);
    }
}
