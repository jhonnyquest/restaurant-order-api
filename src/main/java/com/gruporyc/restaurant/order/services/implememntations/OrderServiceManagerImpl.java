package com.gruporyc.restaurant.order.services.implememntations;

import com.gruporyc.restaurant.order.dao.ItemsRepository;
import com.gruporyc.restaurant.order.dao.OrdersRepository;
import com.gruporyc.restaurant.order.dto.OrderDTO;
import com.gruporyc.restaurant.order.dto.OrderItemDTO;
import com.gruporyc.restaurant.order.dto.SimpleResponse;
import com.gruporyc.restaurant.order.enums.Status;
import com.gruporyc.restaurant.order.model.Item;
import com.gruporyc.restaurant.order.model.Order;
import com.gruporyc.restaurant.order.model.OrderItem;
import com.gruporyc.restaurant.order.services.OrderServiceManager;
import com.gruporyc.restaurant.order.utilities.TextsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.gruporyc.restaurant.order.mappers.ItemMapper.OrderItemDTOFromItem;
import static com.gruporyc.restaurant.order.mappers.ItemMapper.itemFromOrderItemDTO;
import static com.gruporyc.restaurant.order.mappers.OrderMapper.orderFromOrderDTO;

@Component
public class OrderServiceManagerImpl implements OrderServiceManager {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    ItemsRepository itemsRepository;

    @Autowired
    TextsHelper textsHelper;

    @Override
    public SimpleResponse createOrder(OrderDTO newOrder) {
        if(ordersRepository.getOrderById(newOrder.getId()).isPresent()) {
           throw new HttpClientErrorException(HttpStatus.CONFLICT);
        }
        try {
            for (OrderItemDTO orderItem : newOrder.getItems()) {
                Optional<Item> item = itemsRepository.getItemById(orderItem.getId());
                item.ifPresent(item1 -> orderItem.setPrice(item1.getPrice()));
            }
            String orderId = ordersRepository.createOrder(orderFromOrderDTO(newOrder));
            for (OrderItemDTO orderItemDTO : newOrder.getItems()) {
                String itemId = orderItemDTO.getId();
                if (!itemsRepository.getItemById(orderItemDTO.getId()).isPresent()) {
                    itemId = itemsRepository.createItem(itemFromOrderItemDTO(orderItemDTO));
                }
                ordersRepository.createOrderItem(new OrderItem.Builder()
                        .setOrderId(orderId)
                        .setItemId(itemId)
                        .setQuantity(orderItemDTO.getQuantity())
                        .setSubTotal(orderItemDTO.getPrice().multiply(BigDecimal.valueOf(orderItemDTO.getQuantity())))
                        .build());
            }
        }catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new SimpleResponse(true, textsHelper.getTranslation("api.orders.created.message"), null);
    }

    @Override
    public List<OrderDTO> getActiveOrders() {
        List<OrderDTO> orderDTOList = new ArrayList<>();
        List<Order> orders = ordersRepository.getOrdersStatus(Status.CREATED);
        if(orders.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }

        try {
            for (Order order : orders) {
                orderDTOList.add(getOrderDTO(order));
            }
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return orderDTOList;
    }

    @Override
    public SimpleResponse updateOrderStatus(String orderId, String status) {
        if(!ordersRepository.getOrderById(orderId).isPresent())
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        try {
            ordersRepository.updateOrderStatus(orderId, Status.valueOf(status));
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new SimpleResponse(true, textsHelper.getTranslation("api.orders.status.updated.message"), status);
    }

    @Override
    public OrderDTO getOrderById(String orderId) {
        Optional<Order> orderOpt = ordersRepository.getOrderById(orderId);
        if (!orderOpt.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        return getOrderDTO(orderOpt.get());
    }

    @Override
    public SimpleResponse updateOrderItemStatus(String orderId, String itemId, String status) {
        if(!ordersRepository.getOrderById(orderId).isPresent() || !itemsRepository.getItemById(itemId).isPresent())
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        try {
            ordersRepository.updateOrderItemStatus(orderId, itemId, Status.valueOf(status));
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new SimpleResponse(true, textsHelper.getTranslation("api.order.item.status.updated.message"), status);
    }

    private OrderDTO getOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setCustomerId(order.getCustomerId());
        orderDTO.setTable(order.getTable());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setCreateDate(order.getCreateDate());
        orderDTO.setUpdateDate(order.getUpdateDate());

        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
        List<OrderItem> orderItems = ordersRepository.getOrderItems(order.getId());
        BigDecimal total = BigDecimal.valueOf(0.0);
        for (OrderItem orderItem : orderItems) {
            total = total.add(orderItem.getSubTotal());
            Optional<Item> itemOpt = itemsRepository.getItemById(orderItem.getItemId());
            itemOpt.ifPresent(item -> orderItemDTOList.add(OrderItemDTOFromItem(item,
                    orderItem.getQuantity(),
                    orderItem.getSubTotal(),
                    orderItem.getStatus())));
        }

        orderDTO.setItems(orderItemDTOList);
        orderDTO.setTotal(total);
        return orderDTO;
    }
}
