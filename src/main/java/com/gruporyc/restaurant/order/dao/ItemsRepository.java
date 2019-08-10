package com.gruporyc.restaurant.order.dao;

import com.gruporyc.restaurant.order.enums.Status;
import com.gruporyc.restaurant.order.model.Item;
import com.gruporyc.restaurant.order.model.Order;
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
public class ItemsRepository {
    private final DataSource ds;

    @Value("${api.orders.db.url}")
    private String url;

    public ItemsRepository() {
        this.ds = DataSourceSingleton.getInstance();
    }

    public Optional<Item> getItemById(String id) {
        QueryRunner run = new QueryRunner(ds);
        try {
            String query = "SELECT id," +
                    "    name," +
                    "    description," +
                    "    price," +
                    "    status," +
                    "    create_date," +
                    "    update_date " +
                    " FROM items WHERE id = ?";

            Item item = run.query(query,
                    rs -> {
                        if(!rs.next()){
                            return null;
                        }
                        rs.last();

                        return new Item.Builder()
                                .setId(rs.getString(1))
                                .setName(rs.getString(2))
                                .setDescription(rs.getString(3))
                                .setPrice(rs.getBigDecimal(4))
                                .setStatus(rs.getString(    5))
                                .setCreateDate(rs.getString(6))
                                .setUpdateDate(rs.getString(7))
                                .build();
                    }, id);
            return Optional.ofNullable(item);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String createItem(Item item) {
        QueryRunner run = new QueryRunner(ds);

        String itemId = Objects.isNull(item.getId()) ? UUID.randomUUID().toString() : item.getId();
        try {
            Connection conn = ds.getConnection();
            conn.setAutoCommit(false);
            try {
                String insert = "INSERT INTO items (id," +
                        "    name," +
                        "    description," +
                        "    price," +
                        "    status)" +
                        "VALUES " +
                        "('" + itemId + "', " +
                        "'" + item.getName() + "', " +
                        "'" + item.getDescription() + "', " +
                        "'" + item.getPrice() + "', " +
                        "'" + Status.ACTIVE.name() + "');";
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

        return itemId;
    }

}
