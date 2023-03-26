package com.enigma.springjpa.models.dto;

import jakarta.validation.constraints.NotEmpty;

public class CategoryData {

    @NotEmpty(message = "{invalid.name}")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
