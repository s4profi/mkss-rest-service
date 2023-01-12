package com.hsbremen.student.mkss.restservice.model;


import com.hsbremen.student.mkss.restservice.util.Status;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<LineItem> items = new ArrayList<>();
    public Status status = Status.EMPTY;

    //  private int sum;
    //  public int getSum() { return sum; }
    //  public void setSum(int s) { sum = s; }

    //  private String orderId;

    private String customerName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    //  public String getOrderId() { return orderId; }
    //  public void setOrderId() { orderId = String.valueOf(this.hashCode()); }
    //  public void setOrderId(String orderId) { this.orderId = orderId;  }

    public void addProduct(Product product) {
        this.items.add(product);
    }

    //  public void addService(Service service) {
    //    this.items.add(service);
    //}

    public void deleteItem(int itemId) {
        int index = 0;
        for (LineItem i : items){
            if (i.getId() == itemId){break;};
            index = index + 1;
        }
        items.remove(index);
    }

    //  @Override
    //  public String toString() {
    //      return this.items.toString();
    //  }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus(){ return status;};
}
