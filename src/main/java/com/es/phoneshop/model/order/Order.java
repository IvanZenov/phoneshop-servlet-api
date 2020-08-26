package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Order extends BaseEntity {

    private BigDecimal subtotal;
    private BigDecimal totalPrice;
    private String secureId;

    private DeliveryOrderDetail deliveryOrderDetail;
    private PaymentType paymentType;
    private ContactCustomerDetail contactCustomerDetail;
    private static AtomicLong atomicLong = new AtomicLong(0);

    public Order() {
        this.id = atomicLong.incrementAndGet();
    }

    public String getSecureId() {
        return secureId;
    }

    public void setSecureId(String secureId) {
        this.secureId = secureId;
    }

    private List<CartItem> cartItemList;


    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public DeliveryOrderDetail getDeliveryOrderDetail() {
        return deliveryOrderDetail;
    }

    public void setDeliveryOrderDetail(DeliveryOrderDetail deliveryOrderDetail) {
        this.deliveryOrderDetail = deliveryOrderDetail;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public ContactCustomerDetail getContactCustomerDetail() {
        return contactCustomerDetail;
    }

    public void setContactCustomerDetail(ContactCustomerDetail contactCustomerDetail) {
        this.contactCustomerDetail = contactCustomerDetail;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }
}
