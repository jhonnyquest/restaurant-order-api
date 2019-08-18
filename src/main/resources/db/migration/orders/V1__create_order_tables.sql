CREATE TABLE orders (
  `id` varchar(36) PRIMARY KEY,
  `customer_id` varchar(36) NOT NULL,
  `total` double NOT NULL,
  `table` varchar(36) DEFAULT NULL,
  `status` varchar(50),
  `create_date` TIMESTAMP DEFAULT NOW(),
  `update_date` TIMESTAMP DEFAULT NOW()
);

CREATE INDEX order_id ON orders(id);
CREATE INDEX order_customer_id ON orders(customer_id);
CREATE INDEX order_status ON orders(status);

CREATE TABLE items (
    `id` varchar(36) PRIMARY KEY,
    `name` varchar(128) NOT NULL UNIQUE,
    `description` varchar(512),
    `price` double NOT NULL,
    `status` varchar(50),
    `create_date` TIMESTAMP DEFAULT NOW(),
    `update_date` TIMESTAMP DEFAULT NOW()
);

CREATE INDEX item_id ON items(id);
CREATE INDEX item_name ON items(name);
CREATE INDEX item_description ON items(description);
CREATE INDEX item_status ON items(status);

CREATE TABLE order_item (
    `order_id` varchar(36) NOT NULL,
    `item_id` varchar(36) NOT NULL,
    `quantity` integer NOT NULL DEFAULT 1,
    `sub_total` double NOT NULL,
    `status` varchar(50),
    `create_date` TIMESTAMP DEFAULT NOW(),
    `update_date` TIMESTAMP DEFAULT NOW(),
    PRIMARY KEY (order_id, item_id)
);

CREATE INDEX order_item_order_id ON order_item(order_id);
CREATE INDEX order_item_item_id ON order_item(item_id);
CREATE INDEX order_item_status ON order_item(status);


