package com.gruporyc.restaurant.order.dao;

import com.gruporyc.restaurant.order.enums.Status;
import com.gruporyc.restaurant.order.model.Order;
import com.gruporyc.restaurant.order.model.OrderItem;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@Component
public class OrdersRepository {
    private final DataSource ds;

    @Value("${api.orders.db.url}")
    private String url;

    public OrdersRepository() {
        this.ds = DataSourceSingleton.getInstance();
    }

    public Optional<Order> getOrderById(String id) {
        QueryRunner run = new QueryRunner(ds);
        try {
            String query = "SELECT id," +
                    "    customer_id," +
                    "    total," +
                    "    status," +
                    "    create_date," +
                    "    update_date " +
                    " FROM orders WHERE id = ?";

            Order order = run.query(query,
                    rs -> {
                        if(!rs.next()){
                            return null;
                        }
                        rs.last();

                        return new Order.Builder()
                                .setId(rs.getString(1))
                                .setCustomerId(rs.getString(2))
                                .setTotal(rs.getBigDecimal(3))
                                .setStatus(rs.getString(4))
                                .setCreateDate(rs.getString(5))
                                .setUpdateDate(rs.getString(6))
                                .build();
                    }, id);
            return Optional.ofNullable(order);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Order> getOrdersStatus(Status status) {
        QueryRunner run = new QueryRunner(ds);
        try {
            String query = "SELECT id," +
                    "    customer_id," +
                    "    total," +
                    "    status," +
                    "    create_date," +
                    "    update_date " +
                    " FROM orders WHERE status = ?";

            List<Order> orders = run.query(query,
                    rs -> {
                        List<Order> newOrderList = new LinkedList<>();
                        while(rs.next()) {
                            newOrderList.add(new Order.Builder()
                                    .setId(rs.getString(1))
                                    .setCustomerId(rs.getString(2))
                                    .setTotal(rs.getBigDecimal(3))
                                    .setStatus(rs.getString(4))
                                    .setCreateDate(rs.getString(5))
                                    .setUpdateDate(rs.getString(6))
                                    .build()
                            );
                        }
                        return newOrderList;
                    }, status.name());
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateOrderStatus(String id, Status status) {
        try {
            Connection conn = ds.getConnection();
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            try {
                String update = "UPDATE orders " +
                        "SET status = '" + status.name() + "'"+
                        "WHERE " +
                        "id = '" + id + "';";
                stmt.executeUpdate(update);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException(e);
            } finally {
                DbUtils.close(conn);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String createOrder(Order order) {
        QueryRunner run = new QueryRunner(ds);

        String orderId = Objects.isNull(order.getId()) ? UUID.randomUUID().toString() : order.getId();
        try {
            Connection conn = ds.getConnection();
            conn.setAutoCommit(false);
            try {
                String insert = "INSERT INTO orders (id," +
                        "    customer_id," +
                        "    total," +
                        "    status)" +
                        "VALUES " +
                        "('" + orderId + "', " +
                        "'" + order.getCustomerId() + "', " +
                        "'" + order.getTotal() + "', " +
                        "'" + Status.CREATED.name() + "');";
                run.insert(conn, insert, new ScalarHandler<>());
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException(e);
            } finally {
                DbUtils.close(conn);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orderId;
    }

    public List<OrderItem> getOrderItems(String orderId) {
        QueryRunner run = new QueryRunner(ds);
        try {
            String query = "SELECT order_id," +
                    "    item_id," +
                    "    quantity," +
                    "    sub_total," +
                    "    status," +
                    "    create_date," +
                    "    update_date " +
                    " FROM order_item WHERE order_id = ?";

            List<OrderItem> orderItems = run.query(query,
                    rs -> {
                        List<OrderItem> newOrderItemList = new LinkedList<>();
                        while(rs.next()) {
                            newOrderItemList.add(new OrderItem.Builder()
                                    .setOrderId(rs.getString(1))
                                    .setItemId(rs.getString(2))
                                    .setQuantity((long) rs.getInt(3))
                                    .setSubTotal(rs.getBigDecimal(4))
                                    .setStatus(rs.getString(5))
                                    .setCreateDate(rs.getString(6))
                                    .setUpdateDate(rs.getString(7))
                                    .build()
                            );
                        }
                        return newOrderItemList;
                    }, orderId);
            return orderItems;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createOrderItem(OrderItem orderItem) {
        QueryRunner run = new QueryRunner(ds);

        try {
            Connection conn = ds.getConnection();
            conn.setAutoCommit(false);
            try {
                String insert = "INSERT INTO order_item (order_id," +
                        "    item_id," +
                        "    quantity," +
                        "    sub_total," +
                        "    status)" +
                        "VALUES " +
                        "('" + orderItem.getOrderId() + "', " +
                        "'" + orderItem.getItemId() + "', " +
                        orderItem.getQuantity() + ", " +
                        orderItem.getSubTotal() + ", " +
                        "'" + Status.CREATED.name() + "');";
                run.insert(conn, insert, new ScalarHandler<>());
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException(e);
            } finally {
                DbUtils.close(conn);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
