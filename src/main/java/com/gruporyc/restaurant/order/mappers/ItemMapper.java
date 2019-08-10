package com.gruporyc.restaurant.order.mappers;

import com.gruporyc.restaurant.order.dto.OrderItemDTO;
import com.gruporyc.restaurant.order.enums.Status;
import com.gruporyc.restaurant.order.model.Item;

import java.math.BigDecimal;

public class ItemMapper {
    public static OrderItemDTO OrderItemDTOFromItem(Item item, Long quantity, BigDecimal subTotal, String status) {
        OrderItemDTO response = new OrderItemDTO();
        response.setQuantity(quantity);
        response.setSubTotal(subTotal);
        response.setId(item.getId());
        response.setName(item.getName());
        response.setDescription(item.getDescription());
        response.setPrice(item.getPrice());
        response.setStatus(status);
        response.setCreateDate(item.getCreateDate());
        response.setUpdateDate(item.getUpdateDate());

        return response;
    }

    public static Item itemFromOrderItemDTO(OrderItemDTO itemDTO) {
        return new Item.Builder()
                .setId(itemDTO.getId())
                .setName(itemDTO.getName())
                .setDescription(itemDTO.getDescription())
                .setPrice(itemDTO.getPrice())
                .build();
    }
}
