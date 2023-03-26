package com.enigma.springjpa.models.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class SupplierData {
    @NotEmpty(message = "{invalid.name}")
    private String name;
    @NotEmpty(message = "Address is required")
    private String address;
    @NotEmpty(message = "Email is required")
    @Email
    private String email;
    @NotEmpty(message = "Phone is required")
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
