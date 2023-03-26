package com.enigma.springjpa.Repository;

import com.enigma.springjpa.models.enitity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepo extends JpaRepository<Supplier,Long> {
    List<Supplier> findByNameContainsIgnoreCaseOrEmailContainsIgnoreCase(String name, String email);
    Supplier findByEmail(String email);
    List<Supplier> findByNameContains(String name);
}
