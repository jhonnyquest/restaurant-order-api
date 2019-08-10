package com.gruporyc.restaurant.order.dao;

import com.gruporyc.restaurant.order.utilities.TextsHelper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Optional;

@Component
public class DataSourceSingleton {

    private static DataSource instance = null;

//    @Value("${api.orders.db.url}")
//    private static String url;
//    @Value("${api.orders.db.username}")
//    private static String username;
//    @Value("${api.orders.db.password}")
//    private static String password;

    @Autowired
    private TextsHelper textsHelper;

    public DataSourceSingleton() {}

    public static synchronized DataSource getInstance() {
        if (instance == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(Optional.ofNullable(System.getenv("ORDER_DB_URL"))
                    .orElse("jdbc:mysql://localhost:3306/restaurant_orders"));
            config.setUsername(Optional.ofNullable(System.getenv("ORDER_DB_USERNAME"))
                    .orElse("orderuser"));
            config.setPassword(Optional.ofNullable(System.getenv("ORDER_DB_PASSWORD"))
                    .orElse("orderpassword"));
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.addDataSourceProperty("characterEncoding", "UTF-8");
            config.addDataSourceProperty("useUnicode", "true");
            config.addDataSourceProperty("serverTimezone", "UTC");

            instance = new HikariDataSource(config);

            Flyway flyway = new Flyway();
            flyway.setDataSource(instance);
            flyway.setLocations("classpath:db/migration/orders");
            flyway.migrate();
        }
        return instance;
    }
}


