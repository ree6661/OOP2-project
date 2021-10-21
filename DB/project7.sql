CREATE DATABASE IF NOT EXISTS deliveryDB;
USE deliveryDB;
CREATE TABLE IF NOT EXISTS ordrs
	(id_orders int not null , id_categori int,fragile boolean, id_office_sender int,id_office_recipient int,
    delivery_to_address boolean,id_customer_recipient int,id_courier int,id_status int,
    cash_on_delivery double,payed boolean,acceptance_by_sender datetime,customer_delivery datetime,
    PRIMARY KEY (id_orders),FOREIGN KEY (id_categori) REFERENCES categories(id_categori),
    FOREIGN KEY (id_office_sender) REFERENCES office(id_office),FOREIGN KEY (id_office_recipient) REFERENCES office(id_office),
    FOREIGN KEY (id_customer_recipient) REFERENCES customers(id_customer),FOREIGN KEY (id_courier) REFERENCES couriers(id_courier)
    ,FOREIGN KEY (id_status) REFERENCES statuses(id_status));
    
    USE deliveryDB;
    CREATE TABLE IF NOT EXISTS citys
		(id_city int not null,city varchar(45),
        PRIMARY KEY (id_city));
    
    USE deliveryDB;
     CREATE TABLE IF NOT EXISTS companyes
     (id_company int not null,company varchar(45),
     PRIMARY KEY (id_companyes));
     
	USE deliveryDB;
     CREATE TABLE IF NOT EXISTS office
     (id_office int not null,id_company int,id_city int,
     PRIMARY KEY (id_office),FOREIGN KEY (id_company) REFERENCES companyes(id_company),
     FOREIGN KEY (id_city) REFERENCES citys(id_city));
     
     
	USE deliveryDB;
     CREATE TABLE IF NOT EXISTS statuses
     (id_status int not null,status_name varchar(45),
     PRIMARY KEY (id_status));
     
	USE deliveryDB;
     CREATE TABLE IF NOT EXISTS categories
     (id_categori int not null,categori varchar(45),
     PRIMARY KEY (id_categori));
     
	USE deliveryDB;
     CREATE TABLE IF NOT EXISTS customers
     (id_customer int not null,customer varchar(45),phone char(12),id_city int,adress varchar(45),
     PRIMARY KEY (id_customer),FOREIGN KEY (id_city) REFERENCES citys(id_city));
     
     
	USE deliveryDB;
     CREATE TABLE IF NOT EXISTS couriers
     (id_courier int not null,id_office int,courier varchar(45),phone char(12),
     PRIMARY KEY (id_courier),FOREIGN KEY (id_office) REFERENCES office(id_office));
     
	USE deliveryDB;
     CREATE TABLE IF NOT EXISTS admins
     (id_admin int not null,categori varchar(45),phone char(12),
     PRIMARY KEY (id_admin));
     
	USE deliveryDB;
     CREATE TABLE IF NOT EXISTS price_list
     (id_company int,id_categori int, price double,
     PRIMARY KEY (id_company , id_categori),FOREIGN KEY (id_company) REFERENCES companyes(id_company),
    FOREIGN KEY (id_categori) REFERENCES categories(id_categori));