package com.gruporyc.restaurant.order.model;

import java.math.BigDecimal;

/**
 * Item: Data model for Item object
 * @author jmunoz
 * @since 10/08/2019
 * @version 1.0.0
 */
public class Item {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String status;
    private String createDate;
    private String updateDate;

    public Item(String id, String name, String description, BigDecimal price, String status, String createDate,
                String updateDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
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
        private String id;
        private String name;
        private String description;
        private BigDecimal price;
        private String status;
        private String createDate;
        private String updateDate;

        public Item.Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Item.Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Item.Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Item.Builder setPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Item.Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Item.Builder setCreateDate(String createDate) {
            this.createDate = createDate;
            return this;
        }

        public Item.Builder setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
            return this;
        }

        public Item build() {
            return new Item(id, name, description, price, status, createDate, updateDate);
        }
    }

}
