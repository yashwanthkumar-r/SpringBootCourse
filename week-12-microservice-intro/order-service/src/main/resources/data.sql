INSERT INTO orders (order_status, total_price)
VALUES
    ('CONFIRMED', 1249.98),
    ('PENDING', 1849.98),
    ('CANCELLED', 249.99),
    ('DELIVERED', 999.99);


INSERT INTO order_item (product_id, quantity, order_id)
VALUES
    (1, 1, 1),
    (3, 10, 1),

    (2, 13, 2),
    (3, 12, 2),

    (3, 10, 3),

    (1, 1, 4);