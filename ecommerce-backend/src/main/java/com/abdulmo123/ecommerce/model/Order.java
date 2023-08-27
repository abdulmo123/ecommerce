package com.abdulmo123.ecommerce.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="`order`")
@EntityListeners(AuditingEntityListener.class)
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable=false, updatable=false)
    private String name;

    private double totalPrice;

    @CreatedDate
    @Column(name="created_at", nullable=false, updatable=false)
    private Date createdDate;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="order_id")
    private Cart cart;

    public Order () {}

    public Order (String name, double totalPrice, Cart cart) {
        this.name = name;
        this.totalPrice = totalPrice;
        this.cart = cart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", total price='" + totalPrice + '\'' +
                ", date='" + createdDate + '\'' +
                ", cart='" + cart + '\'' +
                '}';
    }
}
