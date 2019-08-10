package com.gruporyc.restaurant.order.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gruporyc.restaurant.order.dto.ItemDTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * OrderItem: Data model of order_item object
 * @author jmunoz
 * @since 10/08/2019
 * @version 1.0.0
 */
public class OrderItem extends ItemDTO{
    private String orderId;
    private String itemId;
    private Long quantity;
    private BigDecimal subTotal;
    private String status;
    private String createDate;
    private String updateDate;

    public OrderItem(String orderId, String itemId, Long quantity, BigDecimal subTotal, String status,
                     String createDate, String updateDate) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.subTotal = subTotal;
        this.status = status;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getItemId() {
        return itemId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public String getStatus() {
        return status;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }
    
    public static class Builder {
        private String orderId;
        private String itemId;
        private Long quantity;
        private BigDecimal subTotal;
        private String status;
        private String createDate;
        private String updateDate;

        public OrderItem.Builder setOrderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public OrderItem.Builder setItemId(String itemId) {
            this.itemId = itemId;
            return this;
        }

        public OrderItem.Builder setQuantity(Long quantity) {
            this.quantity = quantity;
            return this;
        }

        public OrderItem.Builder setSubTotal(BigDecimal subTotal) {
            this.subTotal = subTotal;
            return this;
        }

        public OrderItem.Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public OrderItem.Builder setCreateDate(String createDate) {
            this.createDate = createDate;
            return this;
        }

        public OrderItem.Builder setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(orderId, itemId, quantity, subTotal, status, createDate, updateDate);
        }
    }
}
