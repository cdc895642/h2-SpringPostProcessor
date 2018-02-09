create table student
(
   id integer not null,
   is_student boolean DEFAULT FALSE ,
   my_date DATE DEFAULT '2017-01-01',
   my_datetime TIMESTAMP,
   name varchar(255) not null,
   passport_number varchar(255) not null,
   double_value DOUBLE,
   bigdec_value DECIMAL ,
   primary key(id)
);