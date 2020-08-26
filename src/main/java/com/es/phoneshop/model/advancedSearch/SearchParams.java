package com.es.phoneshop.model.advancedSearch;

import java.math.BigDecimal;

public class SearchParams {
    private String productCode;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private int minStock;

    public SearchParams(String productCode, BigDecimal minPrice, BigDecimal maxPrice, int minStock) {
        this.productCode = productCode;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.minStock = minStock;
    }

    public SearchParams() {
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getMinStock() {
        return minStock;
    }

    public void setMinStock(int minStock) {
        this.minStock = minStock;
    }
}
