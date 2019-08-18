package com.gruporyc.restaurant.order.mappers;

import com.gruporyc.restaurant.order.dto.OrderDTO;
import com.gruporyc.restaurant.order.dto.OrderItemDTO;
import com.gruporyc.restaurant.order.model.Order;

import java.math.BigDecimal;

public class OrderMapper {

    public static Order orderFromOrderDTO(OrderDTO orderDTO) {
        BigDecimal total = BigDecimal.valueOf(0);
        for (OrderItemDTO orderItemDTO : orderDTO.getItems()) {
            total = total.add(orderItemDTO.getPrice().multiply(BigDecimal.valueOf(orderItemDTO.getQuantity())));
        }

        return new Order.Builder()
                .setId(orderDTO.getId())
                .setTotal(total)
                .setTable(orderDTO.getTable())
                .setCustomerId(orderDTO.getCustomerId())
                .build();
    }
}
