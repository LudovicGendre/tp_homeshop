package com.home.homeShop;

public class Customers {
    private String fullname;
    private String address;

    public Customers(String fullname, String address) {
        this.fullname = fullname;
        this.address = address;
    }

    public String getFullname() {
        return fullname;
    }

    public String getAddress() {
        return address;
    }
}
