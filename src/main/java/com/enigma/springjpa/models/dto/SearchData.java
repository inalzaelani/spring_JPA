package com.enigma.springjpa.models.dto;

public class SearchData {

    private String searchKey;
    private String otherSearchKey;

    public String getOtherSearchKey() {
        return otherSearchKey;
    }

    public void setOtherSearchKey(String otherSearchKey) {
        this.otherSearchKey = otherSearchKey;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }
}
