package com.es.phoneshop.model.enums;

import com.es.phoneshop.model.product.Product;

import java.util.Comparator;

public enum SortFieldWithComparator {
    DESCRIPTION(Comparator.comparing(Product::getDescription)),
    PRICE(Comparator.comparing(Product::getPrice)),
    DEFAULT();

    private Comparator<Product> comparator;

    SortFieldWithComparator(Comparator<Product> comparator) {
        this.comparator = comparator;
    }

    SortFieldWithComparator() {
    }

    public static Comparator<Product> sortBy(String key, SortOrder sortOrder) {
        Comparator<Product> comparator = valueOf(key).comparator;
        if (SortOrder.ASC == sortOrder) {
            return comparator;
        } else {
            return comparator.reversed();
        }
    }


}
