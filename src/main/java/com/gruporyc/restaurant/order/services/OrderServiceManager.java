package com.gruporyc.restaurant.order.services;


import com.gruporyc.restaurant.order.dto.OrderDTO;
import com.gruporyc.restaurant.order.dto.SimpleResponse;

import java.util.List;

public interface OrderServiceManager {

    SimpleResponse createOrder(OrderDTO newOrder);

    List<OrderDTO> getActiveOrders();

    SimpleResponse updateOrderStatus (String orderId, String status);

    OrderDTO getOrderById(String orderId);

    SimpleResponse updateOrderItemStatus(String orderId, String itemId, String status);
}
