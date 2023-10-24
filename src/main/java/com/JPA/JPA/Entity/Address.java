package com.JPA.JPA.Entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Address implements Serializable {
    private int houseNumber;

    private String street;

    private String city;

    private String state;

    private String country;

    private int pincode;
}
