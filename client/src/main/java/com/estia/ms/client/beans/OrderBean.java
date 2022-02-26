package com.estia.ms.client.beans;

import java.util.ArrayList;
import java.util.List;

public class OrderBean {

    private Long id;

    private Double price = 0D;

    private List<OrderItemBean> products;

    public OrderBean() {
        this.products = new ArrayList<>();
    }

    public OrderBean(Long id, List<OrderItemBean> products, Double price) {
        this.id = id;
        this.products = products;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<OrderItemBean> getProducts() {
        return products;
    }

    public void addProduct(OrderItemBean product) {
        this.products.add(product);
    }
}
