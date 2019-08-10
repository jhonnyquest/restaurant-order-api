CREATE TRIGGER orders_update_trigger BEFORE UPDATE ON orders FOR EACH ROW SET new.update_date = NOW();

CREATE TRIGGER items_update_trigger BEFORE UPDATE ON items FOR EACH ROW SET new.update_date = NOW();

CREATE TRIGGER order_item_update_trigger BEFORE UPDATE ON order_item FOR EACH ROW SET new.update_date = NOW();