package com.es.phoneshop.model.order;

import java.math.BigDecimal;

public class DeliveryOrderDetail {
    private BigDecimal deliveryCost;
    private String deliveryDate;

    public DeliveryOrderDetail() {
    }

    public DeliveryOrderDetail(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
