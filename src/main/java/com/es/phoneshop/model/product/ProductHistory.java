package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProductHistory {
    private LocalDate startDate;
    private BigDecimal price;

    public ProductHistory(LocalDate startDate, BigDecimal price) {
        this.startDate = startDate;
        this.price = price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
