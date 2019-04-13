/*======================================================
  Creates the database and fills the table with data
======================================================= */

-- Table 3 --
CREATE TABLE login
(
  login_id    INT IDENTITY (1,1) NOT NULL PRIMARY KEY,
  username    VARCHAR(25) UNIQUE NOT NULL,
  password    VARCHAR(25)        NOT NULL,
);

-- Table 4 --
CREATE TABLE permtype
(
  permType_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  perm_desc   VARCHAR(50),
);

CREATE TABLE staff
(
  staff_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  firstName VARCHAR(25) NOT NULL,
  lastName VARCHAR(25) NOT NULL,
  login_id INT NOT NULL,
  permtype_id INT NOT NULL,
  FOREIGN KEY (permtype_id) REFERENCES permtype(permtype_id),
  FOREIGN KEY (login_id) REFERENCES login(login_id)
)

-- Table 2 --
CREATE TABLE status
(
  status_id   INT IDENTITY(1,1)  NOT NULL PRIMARY KEY,
  login_id    INT                NOT NULL,
  status_desc varchar(20)        NOT NULL,
  FOREIGN KEY(login_id) REFERENCES login(login_id)
);

-- Table 1 --
CREATE TABLE loginLog
(
  loginlog_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  user_id     INT                NOT NULL, -- Remove?
  status_id   INT                NOT NULL,
  timestamp   DATE               NOT NULL,
  FOREIGN KEY(status_id) REFERENCES status(status_id)
);

-- Table 5 --
CREATE TABLE employee
(
  employee_id   INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  staff_id   INT                NOT NULL,
  meal_status   INT                NOT NULL
  FOREIGN KEY(staff_id) REFERENCES staff(staff_id)
);

-- Table 6 --
CREATE TABLE emergencyContact
(
  emergency_id       INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  employee_id        INT                NOT NULL,
  contactName        VARCHAR(25)        NOT NULL,
  contactPhoneNumber varchar(25)        NOT NULL,
  FOREIGN KEY (employee_id) REFERENCES employee (employee_id)
);

-- Table 7 --
CREATE TABLE mealStatus
(
  mealStatus_id  INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  employee_id    INT                NOT NULL,
  mealCreditDate DATE               NOT NULL,
  FOREIGN KEY (employee_id) REFERENCES employee (employee_id)
);

-- Table 9 --
CREATE TABLE customer
(
  customer_id   INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  customerPhone VARCHAR(20)        NOT NULL,
  customerEmail VARCHAR(50)        NOT NULL,
  firstName     VARCHAR(25)        NOT NULL,
  lastName      VARCHAR(25)        NOT NULL
);

-- Table 12 --
CREATE TABLE storeLocation
(
  location_id     INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  locationAddress VARCHAR(100)       NOT NULL
);

-- Table 28 --
CREATE TABLE menu
(
  menu_id  INT NOT NULL PRIMARY KEY,
  menuName VARCHAR(25) NOT NULL
);

-- Table 30 --
CREATE TABLE menuItems
(
  menuItem_id   INT          NOT NULL IDENTITY(1,1) PRIMARY KEY,
  menu_id       INT          NOT NULL,
  menuItemName  VARCHAR(255)  NOT NULL,
  menuItemPrice DECIMAL(4,2)      NOT NULL,
  menuItemDesc  VARCHAR(100) NOT NULL,
  FOREIGN KEY (menu_id) REFERENCES menu (menu_id)
);

-- Table 8 --
CREATE TABLE sales
(
  sales_id      INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  employee_id   INT                NOT NULL,
  customer_id   INT                NOT NULL,
  menuItem_id   INT                NOT NULL,
  location_id   INT                NOT NULL,
  redemption_id INT                NOT NULL,
  billTotal     DECIMAL(5, 2)      NOT NULL,
  rewardPoints  INT                NOT NULL,
  FOREIGN KEY (employee_id) REFERENCES employee (employee_id),
  FOREIGN KEY (customer_id) REFERENCES customer (customer_id),
  FOREIGN KEY (location_id) REFERENCES storeLocation (location_id),
  FOREIGN KEY (menuItem_id) REFERENCES menuItems(menuItem_id)
);

-- Table 10 --
CREATE TABLE rewards
(
  reward_id    INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  customer_id  INT                NOT NULL,
  rewardPoints INT                NOT NULL,
  FOREIGN KEY (customer_id) REFERENCES customer (customer_id)
);

-- Table 11 --
CREATE TABLE pointsRedemption
(
  redemption_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  reward_id     INT               NOT NULL,
  redeemStatus  INT               NOT NULL,
  FOREIGN KEY (reward_id) REFERENCES rewards (reward_id)
);

-- Table 26 --
CREATE TABLE admin
(
  admin_id      INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  staff_id      INT NOT NULL,
  FOREIGN KEY (staff_id) REFERENCES staff (staff_id)
);

-- Table 15 --
CREATE TABLE suppliers
(
  supplier_id     INT         NOT NULL IDENTITY(1,1) PRIMARY KEY,
  admin_id        INT         NOT NULL,
  supplier_status INT         NOT NULL,
  supplierName    VARCHAR(25) NOT NULL
);

-- Table 16 --
CREATE TABLE orders
(
  order_id     INT  NOT NULL IDENTITY(1,1) PRIMARY KEY,
  admin_id     INT  NOT NULL,
  supplier_id  INT  NOT NULL,
  orderDate    DATE NOT NULL,
  arrival_date DATE NOT NULL,
  FOREIGN KEY (admin_id) REFERENCES admin (admin_id),
  FOREIGN KEY (supplier_id) REFERENCES suppliers (supplier_id)
);

-- Table 13 --
CREATE TABLE deliveryHistory
(
  delivery_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  order_id    INT                NOT NULL,
  location_id INT                NOT NULL,
  FOREIGN KEY (order_id) REFERENCES orders (order_id),
  FOREIGN KEY (location_id) REFERENCES storeLocation (location_id)
);

-- Table 14 --
CREATE TABLE storeLocation_supplier
(
  location_id INT NOT NULL,
  supplier_id INT NOT NULL,
  FOREIGN KEY (location_id) REFERENCES storeLocation (location_id),
  FOREIGN KEY (supplier_id) REFERENCES suppliers (supplier_id)
);

-- Table 23 --
CREATE TABLE product
(
  product_id   INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  supplier_id  INT                NOT NULL,
  productName  VARCHAR(25)        NOT NULL,
  productPrice DECIMAL(4, 2)       NOT NULL,
  FOREIGN KEY (supplier_id) REFERENCES suppliers (supplier_id)
);

-- Table 17 --
CREATE TABLE orderDetails
(
  orderDetail_id INT    NOT NULL IDENTITY(1,1) PRIMARY KEY,
  product_id     INT    NOT NULL,
  quantity       INT    NOT NULL,
  unitPrice      decimal(5, 2)    NOT NULL,
  paymentStatus  INT    NOT NULL,
  discount       INT    NOT NULL,
  FOREIGN KEY (product_id) REFERENCES product (product_id)
);

-- Table 18 --
CREATE TABLE supplierBill
(
  supplierPayment_id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  order_id           INT NOT NULL,
  FOREIGN KEY (order_id) REFERENCES orders (order_id)
);

-- Table 19 --
CREATE TABLE supplier_product
(
  supplier_id INT NOT NULL,
  product_id  INT NOT NULL,
  FOREIGN KEY (supplier_id) REFERENCES suppliers (supplier_id),
  FOREIGN KEY (product_id) REFERENCES product (product_id)
);

-- Table 22 --
CREATE TABLE discounts
(
  discount_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  discountAmt INT                NOT NULL
);

-- Table 20 --
CREATE TABLE supplierEmployee
(
  supplierEmp_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  supplierEmpPhone VARCHAR(20) NOT NULL,
  supplier_id INT NOT NULL,
  discount_id INT NOT NULL
  FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id),
  FOREIGN KEY (discount_id) REFERENCES discounts(discount_id)
);

-- Table 21 --
CREATE TABLE orderdetails_product
(
  order_id   INT NOT NULL,
  product_id INT NOT NULL,
  FOREIGN KEY (order_id) REFERENCES orders (order_id),
  FOREIGN KEY (product_id) REFERENCES product (product_id)
);

-- Table 24 --
CREATE TABLE inventory
(
  inventory_id    INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  product_id      INT                NOT NULL,
  currentProdAmt  DECIMAL(4, 1)                NOT NULL,
  requiredProdAmt DECIMAL(4, 1)                NOT NULL,
  FOREIGN KEY (product_id) REFERENCES product (product_id)
);

-- Table 25 --
CREATE TABLE category
(
  category_id  INT         NOT NULL IDENTITY(1,1) PRIMARY KEY,
  inventory_id INT         NOT NULL,
  categoryName VARCHAR(25) NOT NULL,
  FOREIGN KEY (inventory_id) REFERENCES inventory (inventory_id)
);

-- Table 27 --
CREATE TABLE admin_menu
(
  menu_id  INT NOT NULL,
  admin_id INT NOT NULL,
  FOREIGN KEY (menu_id) REFERENCES menu (menu_id),
  FOREIGN KEY (admin_id) REFERENCES admin (admin_id)
);


/*-- Table 29 --
CREATE TABLE menuHistory
(
  menuHistory_id INT    NOT NULL AUTO_INCREMENT PRIMARY KEY,
  menu_id        INT    NOT NULL,
  beforeAdj      INT(1) NOT NULL,
  afterAdj       INT(1) NOT NULL,
  time_stamp     DATE   NOT NULL,
  FOREIGN KEY (menu_id) REFERENCES menu (menu_id)
);*/

INSERT INTO menu (menu_id, menuName) VALUES (1, 'Seasonal');
INSERT INTO menu (menu_id, menuName) VALUES (2, 'Non-Seasonal');

INSERT INTO permtype (perm_desc) VALUES ('Admin');
INSERT INTO permtype (perm_desc) VALUES ('Manager');
INSERT INTO permtype (perm_desc) VALUES ('Employee');

BULK INSERT crawfish.dbo.login
  FROM 'C:\login.tsv'
  WITH(
  FIELDTERMINATOR = '\t',
  ROWTERMINATOR = '\n',
  FIRSTROW = 2
  )

BULK INSERT crawfish.dbo.menuItems
    FROM 'C:\menu_item.tsv'
    WITH(
      FIELDTERMINATOR = '\t',
      ROWTERMINATOR = '\n',
      FIRSTROW = 2
    )

BULK INSERT crawfish.dbo.staff
    FROM 'C:\staff.tsv'
    WITH(
      FIELDTERMINATOR = '\t',
      ROWTERMINATOR = '\n',
      FIRSTROW = 2
  )

INSERT INTO suppliers (admin_id, supplier_status, supplierName) VALUES (1, 1, 'Fish Market');
INSERT INTO suppliers (admin_id, supplier_status, supplierName) VALUES (1, 1, 'Crab Market');
INSERT INTO suppliers (admin_id, supplier_status, supplierName) VALUES (1, 1, 'Crawfish Market');

INSERT INTO product (supplier_id, productName, productPrice) VALUES (3, 'Crawfish', 10.29);
INSERT INTO product (supplier_id, productName, productPrice) VALUES (3, 'Crawfish L', 2.29);
INSERT INTO product (supplier_id, productName, productPrice) VALUES (3, 'Crawfish R', 2.29);
INSERT INTO product (supplier_id, productName, productPrice) VALUES (3, 'Crawfish T', 2.29);
INSERT INTO product (supplier_id, productName, productPrice) VALUES (2, 'Blue Crab', 5.29);
INSERT INTO product (supplier_id, productName, productPrice) VALUES (2, 'Blue Crab L', 5.29);
INSERT INTO product (supplier_id, productName, productPrice) VALUES (2, 'Blue Crab R', 5.29);
INSERT INTO product (supplier_id, productName, productPrice) VALUES (2, 'Blue Crab Head', 5.29);
INSERT INTO product (supplier_id, productName, productPrice) VALUES (2, 'Fish Tail', 7.29);
INSERT INTO product (supplier_id, productName, productPrice) VALUES (2, 'Fish Head', 8.29);
INSERT INTO product (supplier_id, productName, productPrice) VALUES (2, 'Fish Body', 19.29);

INSERT INTO inventory (product_id, currentProdAmt, requiredProdAmt) VALUES (2, 5, 9);
INSERT INTO inventory (product_id, currentProdAmt, requiredProdAmt) VALUES (3, 3, 9);
INSERT INTO inventory (product_id, currentProdAmt, requiredProdAmt) VALUES (5, 2, 9);
INSERT INTO inventory (product_id, currentProdAmt, requiredProdAmt) VALUES (1, 1, 9);
INSERT INTO inventory (product_id, currentProdAmt, requiredProdAmt) VALUES (4, 8, 9);
