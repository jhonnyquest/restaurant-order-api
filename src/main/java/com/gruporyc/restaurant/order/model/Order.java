package com.gruporyc.restaurant.order.model;

import java.math.BigDecimal;

/**
 * Order: Data model of Order objects
 * @author jmunoz
 * @since 10/08/2019
 * @version 1.0.0
 */
public class Order {
    private String id;
    private String customerId;
    private BigDecimal total;
        private String status;
        private String createDate;
        private String updateDate;


    public Order(String id, String customerId, BigDecimal total, String status, String createDate, String updateDate) {
        this.id = id;
        this.customerId = customerId;
        this.total = total;
        this.status = status;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public static class Builder {
        private String id;
        private BigDecimal total;
        private String status;
        private String customerId;
        private String createDate;
        private String updateDate;

        public Order.Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Order.Builder setTotal(BigDecimal total) {
            this.total = total;
            return this;
        }

        public Order.Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Order.Builder setCustomerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public Order.Builder setCreateDate(String createDate) {
            this.createDate = createDate;
            return this;
        }

        public Order.Builder setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
            return this;
        }

        public Order build() {
            return new Order(id, customerId, total, status, createDate, updateDate);
        }
    }
}
