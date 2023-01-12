package com.hsbremen.student.mkss.restservice.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderInputData {

    @JsonProperty
    private String customerName;
    @JsonProperty
    private String name;
    @JsonProperty

    private int price;
    @JsonProperty

    private int quantity;
    @JsonProperty

    private int persons;
    @JsonProperty

    private int hours;

    /**
     * Builder Pattern
     */
    public OrderInputData() {
        //empty constructor
    }

    public OrderInputData(String name) {
        if(name == null)
            throw new IllegalArgumentException("Invalid name");
        this.name = name;
    }

    public OrderInputData price(int price) {
        this.price = price;
        return this;
    }

    public OrderInputData quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderInputData persons(int persons) {
        this.persons = persons;
        return this;
    }

    public OrderInputData hours(int hours) {
        this.hours = hours;
        return this;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public int getPersons() { return persons; }
    public int getHours() { return hours; }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

}