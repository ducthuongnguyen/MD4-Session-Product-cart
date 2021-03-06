package com.codegym.model;

import com.codegym.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    @Autowired
    IProductRepository productRepository;
    private Map<Product, Integer> products = new HashMap<>();

    public Cart() {
    }

    public Cart(Map<Product, Integer> products) {
        this.products = products;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    private boolean checkItemInCart(Product product) {
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            if (entry.getKey().getId().equals(product.getId())) {
                return true;
            }
        }
        return false;
    }

    private Map.Entry<Product, Integer> selectItemInCart(Product product) {
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            if (entry.getKey().getId().equals(product.getId())) {
                return entry;
            }
        }
        return null;
    }

    public Map.Entry<Product, Integer> selectProductInCart(Long id) {
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            if (entry.getKey().getId() == id) {
                return entry;
            }
        }
        return null;
    }

    public void deleteById( Map.Entry<Product, Integer> product) {
        products.remove(product.getKey());
    }


    public void addProduct(Product product) {
        if (!checkItemInCart(product)) {
            products.put(product, 1);
        } else {
            Map.Entry<Product, Integer> itemEntry = selectItemInCart(product);
            Integer newQuantity = itemEntry.getValue() + 1;
            products.replace(itemEntry.getKey(), newQuantity);
        }
    }

    public void removeProduct(Product product) {
//        if (!checkItemInCart(product)){
//            products.put(product,1);
//        } else {
        Map.Entry<Product, Integer> itemEntry = selectItemInCart(product);
        Integer newQuantity = itemEntry.getValue() - 1;
        products.replace(itemEntry.getKey(), newQuantity);
//        }
    }


    public Integer countProductQuantity() {
        Integer productQuantity = 0;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            productQuantity += entry.getValue();
        }
        return productQuantity;
    }

    public Integer countItemQuantity() {
        return products.size();
    }

    public Float countTotalPayment() {
        float payment = 0;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            payment += entry.getKey().getPrice() * (float) entry.getValue();
        }
        return payment;
    }
}