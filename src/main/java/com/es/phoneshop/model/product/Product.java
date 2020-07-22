package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Product {
    private Long id;
    private String code;
    private String description;
    /** null means there is no price because the product is outdated or new */
    private BigDecimal price;
    /** can be null if the price is null */
    private Currency currency;
    private int stock;
    private String imageUrl;
    private static AtomicLong atomicLong = new AtomicLong(0);
    private List<PriceHistory> histories;


    public Product() {
    }

    public Product(String code, String description, BigDecimal price, Currency currency,
                   int stock, String imageUrl, List<PriceHistory> histories) {
        this.id = atomicLong.incrementAndGet();
        this.code = code;
        this.description = description;
        this.currency = currency;
        this.stock = stock;
        this.price = price;
        this.imageUrl = imageUrl;
        this.histories = histories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<PriceHistory> getHistories() {
        return histories;
    }

    public void setHistories(List<PriceHistory> histories) {
        this.histories = histories;
    }
}