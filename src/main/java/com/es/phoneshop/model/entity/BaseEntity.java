package com.es.phoneshop.model.entity;

import java.io.Serializable;

public class BaseEntity implements Serializable, Cloneable {
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
