INSERT INTO users (id, email, name, password)
VALUES (100, 'admin@gmail.com', 'Admin', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2'); --123
INSERT INTO users (id, email, name, password)
VALUES (200, 'source@gmail.com', 'abe', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2'); --123
INSERT INTO users (id, email, name, password)
VALUES (300, 'foo@gmail.com', 'Mtek', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2');
--123

--
--
INSERT INTO role (role)
VALUES ('ADMIN');
INSERT INTO role (role)
VALUES ('MERCHANT');
INSERT INTO role (role)
VALUES ('CUSTOMER');


-- INSERT INTO users_role (user_id, role_id)
-- VALUES (100, 1);
-- INSERT INTO users_role (user_id, role_id)
-- VALUES (200, 2);
-- INSERT INTO users_role (user_id, role_id)
-- VALUES (300, 3);
--
--
--
--
-- -- New sample data

-- Insert data into 'address' table
-- INSERT INTO address (id, addressType, street, city, state, zipCode, country)
-- VALUES (1, 'SHIPPING', '123 Main St', 'New York', 'NY', '10001', 'USA'),
--        (2, 'BILLING', '456 Elm St', 'Los Angeles', 'CA', '90001', 'USA'),
--        (3, 'SHIPPING', '789 Pine St', 'Chicago', 'IL', '60601', 'USA'),
--        (4, 'BILLING', '321 Oak St', 'Houston', 'TX', '77001', 'USA'),
--        (5, 'BILLING', '322 Oak St', 'Houston', 'TX', '77001', 'USA'),
--        (6, 'BILLING', '323 Oak St', 'Houston', 'TX', '77001', 'USA'),
--        (7, 'BILLING', '324 Oak St', 'Houston', 'TX', '77001', 'USA'),
--        (8, 'SHIPPING', '654 Cedar St', 'Philadelphia', 'PA', '19019', 'USA');
-- --Users
-- INSERT INTO users (id, name, email, firstName, lastName, password, billingAddress)
-- VALUES (20, 'johndoe2', 'john.doe2@example.com', 'John', 'Doe', 'johndoe123', 2),
--        (21, 'janedoe2', 'jane.doe2@example.com', 'Jane', 'Doe', 'janedoe123', 4),
--        (22, 'youkim', 'youkim@example.com', 'CUSTOMER', 'User', 'admin123', 5),
--        (23, 'merchant2', 'teke@example.com', 'MERCHANT', 'User', 'merchant123', 6),
--        (24, 'customer2', 'Naod@example.com', 'Customer', 'User', 'customer123', 7);

-- Insert data into 'address' table
INSERT INTO address (id, addressType, street, city, state, zipCode, country)
VALUES (11, 'SHIPPING', '123 Main St', 'New York', 'NY', '10001', 'USA'),
       (12, 'BILLING', '456 Elm St', 'Los Angeles', 'CA', '90001', 'USA'),
       (13, 'SHIPPING', '789 Pine St', 'Chicago', 'IL', '60601', 'USA'),
       (14, 'BILLING', '321 Oak St', 'Houston', 'TX', '77001', 'USA'),
       (15, 'SHIPPING', '322 Oak St', 'Houston', 'TX', '77001', 'USA'),
       (16, 'SHIPPING', '323 Oak St', 'Houston', 'TX', '77001', 'USA'),
       (17, 'SHIPPING', '324 Oak St', 'Houston', 'TX', '77001', 'USA'),
       (18, 'SHIPPING', '654 Cedar St', 'Philadelphia', 'PA', '19019', 'USA');

-- Insert data into 'users' table
INSERT INTO users (id, name, email, firstName, lastName, password, billingAddress, defaultShippingAddress)
VALUES (20, 'johndoe2', 'john.doe2@example.com', 'John', 'Doe', 'johndoe123', 12, 11),
       (21, 'janedoe2', 'jane.doe2@example.com', 'Jane', 'Doe', 'janedoe123', 14, 13),
       (22, 'youkim', 'youkim@example.com', 'CUSTOMER', 'User', 'admin123', 14, 15),
       (23, 'merchant2', 'teke@example.com', 'MERCHANT', 'User', 'merchant123', 12, 16),
       (24, 'customer2', 'Naod@example.com', 'Customer', 'User', 'customer123', 12, 17);



INSERT INTO users_role (user_id, role_id)
VALUES (100, 1); --100 is admin
INSERT INTO users_role (user_id, role_id)
VALUES (200, 2); --200 is Merchant
INSERT INTO users_role (user_id, role_id)
VALUES (300, 3);
INSERT INTO users_role (user_id, role_id)
VALUES (20, 3);
INSERT INTO users_role (user_id, role_id)
VALUES (21, 3);
INSERT INTO users_role (user_id, role_id)
VALUES (22, 3);
INSERT INTO users_role (user_id, role_id)
VALUES (23, 2);
INSERT INTO users_role (user_id, role_id)
VALUES (24, 3);

-- You can also add multiple roles to the same user
-- For example, to make User1 also an ADMIN:
-- INSERT INTO user_role (user_id, role_id) VALUES (1, 3);  (since our relationship is @ManyToMany)


INSERT INTO useraddress (user_id, address_id)
VALUES (20, 11),
       (20, 13),
       (21, 14),
       (22, 18),--why not 3(duplicate not allowed)?
       (21, 15),
       (24, 12);


-- Cart
INSERT INTO cart (cartId, customer)
VALUES (11, 20),
       (12, 21),
       (13, 22),
       (14, 23),
       (15, 24);

-- Item
INSERT INTO item (name, description, price, image, barcode, quantityinstock, merchant)
VALUES ( 'Item 1', 'Description 1', 100, 'Image 1', '123456789012', 10, 23),
       ('Item 2', 'Description 2', 200, 'Image 2', '234567890123', 20, 23),
       ( 'Item 3', 'Description 3', 300, 'Image 3', '345678901234', 30, 23),
       ( 'Item 4', 'Description 4', 400, 'Image 4', '456789012345', 40, 23),
       ('Item 5', 'Description 5', 500, 'Image 5', '567890123456', 50, 23);
INSERT INTO item (itemId, name, description, price, image, barcode, quantityinstock, merchant)
VALUES (11, 'Item 1', 'Description 1', 100, 'Image 1', '123456789012', 10, 23),
       (12, 'Item 2', 'Description 2', 200, 'Image 2', '234567890123', 20, 23),
       (13, 'Item 3', 'Description 3', 300, 'Image 3', '345678901234', 30, 23),
       (14, 'Item 4', 'Description 4', 400, 'Image 4', '456789012345', 40, 23),
       (15, 'Item 5', 'Description 5', 500, 'Image 5', '567890123456', 50, 23);


-- CreditCard
INSERT INTO creditcard (creditCardId, cardNumber, expirationDate, securityCode, balance, user_id)
VALUES (11, '4111111111111111', '2025-12-31', '123', 5000, 20),
       (12, '5500000000000004', '2026-12-31', '456', 3000, 20),
       (13, '340000000000009', '2027-12-31', '789', 7000, 21),
       (14, '6011000000000004', '2024-12-31', '321', 6000, 21),
       (15, '30000000000004', '2028-12-31', '654', 8000, 24);


-- LineItem
INSERT INTO LineItem (lineItemId, itemId, quantity, discount, cart_id)
VALUES (11, 11, 2, 10, 11),
       (12, 12, 3, 15, 12),
       (13, 13, 1, 5, 13),
       (14, 14, 5, 20, 14),
       (15, 15, 4, 25, 15);


-- Order
INSERT INTO orderTable (orderId, customer, shippingAddressId, OrderStatus, orderDate)
VALUES (11, 20, 11, 'DELIVERED', '2023-05-20 10:00:00'),
       (12, 21, 13, 'SHIPPED', '2023-05-21 11:00:00'),
       (13, 24, 15, 'PLACED', '2023-05-22 12:00:00'),
       (14, 20, 11, 'PROCESSED', '2023-05-23 13:00:00'),
       (15, 21, 13, 'PLACED', '2023-05-24 14:00:00');


-- Review
INSERT INTO review ( title, description, numberOfStars, reviewDate, buyer, itmeId)
VALUES ('Great Product', 'I loved it!', 5, '2023-05-21 15:00:00', 20, 1),
       ('Decent', 'Could be better', 3, '2023-05-22 16:00:00', 21, 2),
       ('Fantastic', 'Exceeds expectations', 5, '2023-05-23 17:00:00', 24, 3),
       ('Bad Product', 'I hate it!', 1, '2023-05-24 18:00:00', 20, 4),
       ('Average', 'Met expectations', 3, '2023-05-25 19:00:00', 21, 5);
