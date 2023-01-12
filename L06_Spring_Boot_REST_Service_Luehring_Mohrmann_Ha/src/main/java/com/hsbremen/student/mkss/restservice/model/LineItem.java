package com.hsbremen.student.mkss.restservice.model;

import jakarta.persistence.*;
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class LineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void setPrice(int price);

    public abstract int getPrice();

    public abstract void print();
}