/*======================================================
  Creates the database and fills the table with data
======================================================= */

# <--Table 1-->
CREATE TABLE status
(
  status_id int not null,
  status_desc varchar(255),
  PRIMARY KEY (status_id)
);


# <--Table 2-->
CREATE TABLE login_Status
(
  login_status_id int AUTO_INCREMENT not null,
  user_id int not null,
  status_id int not null,
  PRIMARY KEY (login_status_id),
  FOREIGN KEY (status_id) REFERENCES status(status_id),
  FOREIGN KEY (user_id) REFERENCES login(user_id)
);

# <--Table 3-->
CREATE TABLE loginlog
(
  loginlog_id int AUTO_INCREMENT not null,
  timestamp DATE not null,
  PRIMARY KEY (loginlog_id),
  FOREIGN KEY (loginlog_id) REFERENCES login_Status(login_status_id)
);

# <--Table 4-->
CREATE TABLE login
(
  user_id int AUTO_INCREMENT not null,
  username varchar(25) unique not null,
  password varchar(25) not null,
  PRIMARY KEY (user_id)
);

# <--Table 5-->
CREATE TABLE login_permtype
(
  login_permtype_id int AUTO_INCREMENT not null,
  user_id int not null,
  permission_id int not null,
  PRIMARY KEY (login_permtype_id),
  FOREIGN KEY (user_id) REFERENCES login(user_id),
  FOREIGN KEY (permission_id) REFERENCES permtype(permission_id)
);

# <--Table 6-->
CREATE TABLE permtype
(
  permission_id int AUTO_INCREMENT not null,
  permDesc varchar(255) not null,
  PRIMARY KEY (permission_id)
);



/*CREATE TABLE login
(
  username varchar(25) not null unique primary key ,
  password varchar(24) not null,
  role int(1) NOT NULL,
  firstname VARCHAR(25) not null,
  lastname varchar(25),
  phone varchar(20)
);
*/
<--Table 2-->
CREATE TABLE status
(
  status_id int not null unique primary key,
  user_id int
)