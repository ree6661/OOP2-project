CREATE DATABASE IF NOT EXISTS deliveryDB;
	USE deliveryDB;
     CREATE TABLE IF NOT EXISTS categories
     (id_category int not null auto_increment,categori varchar(45),
     PRIMARY KEY (id_category));

    
    USE deliveryDB;
    CREATE TABLE IF NOT EXISTS cities
		(id_city int not null auto_increment,city varchar(45),
        PRIMARY KEY (id_city));
    
    USE deliveryDB;
     CREATE TABLE IF NOT EXISTS companies
     (id_company int not null auto_increment,company varchar(45),
     PRIMARY KEY (id_company));
     
	USE deliveryDB;
     CREATE TABLE IF NOT EXISTS office
     (id_office int not null auto_increment,id_company int,id_city int,adress varchar(45),
     PRIMARY KEY (id_office),FOREIGN KEY (id_company) REFERENCES companies(id_company),
     FOREIGN KEY (id_city) REFERENCES cities(id_city));
     
     
	USE deliveryDB;
     CREATE TABLE IF NOT EXISTS statuses
     (id_status int not null auto_increment,status_name varchar(45),
     PRIMARY KEY (id_status));
     
	USE deliveryDB;
     CREATE TABLE IF NOT EXISTS customers
     (id_customer int not null auto_increment,name varchar(45),phone char(12),id_city int,address varchar(45),password varchar(30) not null,
     PRIMARY KEY (id_customer),FOREIGN KEY (id_city) REFERENCES cities(id_city));
     
     
	USE deliveryDB;
     CREATE TABLE IF NOT EXISTS couriers
     (id_courier int not null auto_increment,id_office int,name varchar(45),phone char(12),password varchar(30) not null,
     PRIMARY KEY (id_courier),FOREIGN KEY (id_office) REFERENCES office(id_office));
     
	USE deliveryDB;
     CREATE TABLE IF NOT EXISTS admins
     (id_admin int not null auto_increment,name varchar(45),phone char(12),password varchar(30) not null,
     PRIMARY KEY (id_admin));
     
	USE deliveryDB;
     CREATE TABLE IF NOT EXISTS price_list
     (id_company int,id_category int, price double,
     PRIMARY KEY (id_company , id_category),FOREIGN KEY (id_company) REFERENCES companies(id_company),
    FOREIGN KEY (id_category) REFERENCES categories(id_category));
    
    CREATE TABLE IF NOT EXISTS orders
	(id_orders int not null auto_increment, id_category int,fragile boolean, id_office_sender int,id_office_recipient int,
    delivery_to_address boolean,id_customer_sender int,id_customer_recipient int,id_courier int,id_status int,
    cash_on_delivery double,paid boolean,acceptance_by_sender datetime,customer_delivery datetime,
    PRIMARY KEY (id_orders),FOREIGN KEY (id_category) REFERENCES categories(id_category),
    FOREIGN KEY (id_office_sender) REFERENCES office(id_office),FOREIGN KEY (id_office_recipient) REFERENCES office(id_office),
   FOREIGN KEY (id_customer_sender) REFERENCES customers(id_customer), FOREIGN KEY (id_customer_recipient) REFERENCES customers(id_customer),FOREIGN KEY (id_courier) REFERENCES couriers(id_courier)
    ,FOREIGN KEY (id_status) REFERENCES statuses(id_status));
