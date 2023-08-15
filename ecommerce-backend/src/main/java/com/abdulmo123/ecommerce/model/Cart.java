package com.abdulmo123.ecommerce.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String name;
    private double currentPrice;

    @OneToMany
    @JoinColumn(name="cart_id")
    private List<Product> cartProducts = new ArrayList<>();


    public Cart () {}

    public Cart(String name, double currentPrice, List<Product> cartProducts) {
        this.name = name;
        this.currentPrice = currentPrice;
        this.cartProducts = cartProducts;
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

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public List<Product> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<Product> cartProducts) {
        this.cartProducts = cartProducts;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", name=" + name +
                ", current price='" + currentPrice + '\'' +
                ", products in cart='" + cartProducts + '\'' +
                '}';
    }
}
