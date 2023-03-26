package com.enigma.springjpa.Repository;

import com.enigma.springjpa.models.dto.SearchData;
import com.enigma.springjpa.models.enitity.Product;
import com.enigma.springjpa.models.enitity.Supplier;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends CrudRepository<Product,Long> {

    @Query("select p from Product p where p.name like :name")
    public List<Product> findByName(@PathParam("name")String name);

    @Query("select p from Product p where p.category.id = :categoryId")
    public List<Product> findByCategoryId(@PathParam("categoryId") Long categoryId);

    @Query("select p from Product  p where :supplier member of p.supplier")
    public List<Product> findBySupplier(@PathParam("supplier")Supplier supplier);
}
