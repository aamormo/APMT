--
-- Table structure for table `clients`
--

  --id integer IDENTITY NOT NULL,
--DROP TABLE IF EXISTS CLIENTS;
--CREATE TABLE CLIENTS (
--  id integer GENERATED BY DEFAULT AS IDENTITY (START WITH 1) PRIMARY KEY,
--  code varchar(20) default NULL,
--  name varchar(100) default NULL,
--  alias varchar(20) default NULL,
--  fiscal_identifier varchar(12) default NULL,
--  fiscal_address varchar(80) default NULL,
--  invoicing_address varchar(80) default NULL,
--  email varchar(45) default NULL,
--  telephone varchar(25) default NULL,
--  date_from date NOT NULL,
--  date_until date default NULL,
--  contact_person varchar(60) default NULL,
--  contact_email varchar(45) default NULL,
--  contact_telephone varchar(25) default NULL,
--  contact_fax varchar(25) default NULL,
--  comments varchar(2000) default NULL,
--  active tinyint default 0,
--  user_add varchar(20) NOT NULL,
--  user_delete varchar(20) default NULL,
--  user_update varchar(20) NOT NULL,
--  date_add timestamp default CURRENT_TIMESTAMP,
--  date_delete timestamp default NULL,
--  date_update timestamp default CURRENT_TIMESTAMP,
--  PRIMARY KEY (id),
--  UNIQUE (code)
--);

--INSERT INTO CLIENTS (code, name, alias, date_from, active, user_add, user_update) 
--				VALUES ('CLIENT0001', 'Banco de Espana', 'BdE', '2008-01-01 00:00:00', 1, 'alvaro.amor', 'alvaro.amor');
--INSERT INTO CLIENTS (code, name, alias, date_from, active, user_add, user_update) 
--				VALUES ('CLIENT0002', 'La Caixa', 'Caixa', '2009-01-01 00:00:00', 1, 'alvaro.amor', 'alvaro.amor');
--INSERT INTO CLIENTS (code, name, alias, date_from, active, user_add, user_update) 
--				VALUES ('CLIENT0003', 'Everis', 'Everis', '2010-01-01 00:00:00', 1, 'alvaro.amor', 'alvaro.amor');

--INSERT INTO CLIENTS (code, name, alias) 
--				VALUES ('CLIENT0001', 'Banco de Espana', 'BdE');
--INSERT INTO CLIENTS (code, name, alias) 
--				VALUES ('CLIENT0002', 'La Caixa', 'Caixa');
--INSERT INTO CLIENTS (code, name, alias) 
--				VALUES ('CLIENT0003', 'Everis', 'Everis');