package com.hsbremen.student.mkss.restservice.model;

import jakarta.persistence.*;

@Entity
public class Service extends LineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private int hours, persons;
    private int rate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Service(String name, int persons, int hours) {
        setName(name);
        setHours(hours); // in Product its this.hours/persons
        setPersons(persons);
        this.rate = 1242;
    }

    public Service() {

    }

    // setters & getters
    public void setPrice(int rate) { this.rate = rate; }
    public int getPrice() {
        return this.rate * hours * persons;
    }

    private void setPersons(int persons) { this.persons = persons; }
    public int getPersons() { return this.persons; }

    private void setHours(int hours) { this.hours = hours; }
    public int getHours() { return this.hours; }

    public void print() {
        System.out.println(persons + " persons for " + hours + "h of " + getName());
    }

    public String toString() {
        return persons + " persons for " + hours + "h of " + getName();
    }
}