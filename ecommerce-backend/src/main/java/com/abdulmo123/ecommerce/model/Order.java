package com.abdulmo123.ecommerce.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private Date createdDate;
    private double totalPrice;

    @OneToMany
    @JoinColumn(name="order_id")
    private List<Product> orderProducts = new ArrayList<>();

    public Order () {}

    public Order (Date createdDate, double totalPrice, List<Product> orderProducts) {
        this.createdDate = createdDate;
        this.totalPrice = totalPrice;
        this.orderProducts = orderProducts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<Product> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<Product> orderProducts) {
        this.orderProducts = orderProducts;
    }
}
