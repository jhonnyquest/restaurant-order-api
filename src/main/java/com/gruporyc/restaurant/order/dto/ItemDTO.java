package com.gruporyc.restaurant.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * OrderItemDTO: Data transformation object for json transformation of Item object
 * @author jmunoz
 * @since 31/07/2019
 * @version 1.0.0
 */
public class ItemDTO {
    private String id;

    @NotNull(message = "is required")
    @Min(value = 3, message = "must be greater than or equal to 3")
    @Max(value = 512, message = "must be less than or equal to 512")
    private String name;

    private String description;

    @NotNull(message = "is required")
    private BigDecimal price;

    /**
     * @return the Order ItemDTO's universal identifier
     */
    @JsonProperty("product_id")
    public String getId() {
        return id;
    }

    /**
     * @param id the item's universal identifier
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the Order ItemDTO's commercial name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * @param name the item's universal name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the Order ItemDTO's commercial description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * @param description the item's commercial description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the Order ItemDTO's price
     */
    @JsonProperty("price")
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the item's price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderItemDTO{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", description='" + description + "'" +
                ", price=" + price +
                '}';
    }
}
