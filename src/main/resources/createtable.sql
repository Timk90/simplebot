DROP TABLE IF EXISTS details.student;
CREATE TABLE  details.student(
   ID int(20) NOT NULL AUTO_INCREMENT,
   NAME varchar(30) NOT NULL,
   SURENAME varchar(30) NOT NULL,
   BIRTH varchar(10) NOT NULL,
   COUNTRY varchar(30),
   TOWN varchar(30),
   ZIPCODE varchar(6),
   PRIMARY KEY ( ID )
)