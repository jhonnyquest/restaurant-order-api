package com.gruporyc.restaurant.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gruporyc.restaurant.order.enums.Status;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * OrderItemDTO: Data transformation object for json transformation of Order ItemDTO object
 * @author jmunoz
 * @since 31/07/2019
 * @version 1.0.0
 */
public class OrderItemDTO extends ItemDTO{
    @NotNull(message = "is required")
    @Min(value = 1, message = "must be greater than or equal to 1")
    private Long quantity;
    private BigDecimal subTotal;
    private String status;
    private String createDate;
    private String updateDate;

    /**
     * @return the Order Item's quantity
     */
    @JsonProperty("quantity")
    public Long getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the order item's quantity
     */
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the Order Item's subTotal
     */
    @JsonProperty("total")
    public BigDecimal getSubTotal() {
        return subTotal;
    }

    /**
     * @param subTotal the order item's subTotal
     */
    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    /**
     * @return the Order item status
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     * @param status the order item status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the created date
     */
    @JsonProperty("created_date")
    public String getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the created date
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the updated date
     */
    @JsonProperty("updated_date")
    public String getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate the updated date
     */
    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "OrderItemDTO{" +
                ", quantity=" + quantity +
                ", subTotal=" + subTotal +
//                ", item=" + item +
                '}';
    }
}
