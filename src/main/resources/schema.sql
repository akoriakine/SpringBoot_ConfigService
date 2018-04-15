create table IF NOT EXISTS config
(
   id integer not null auto_increment,
   appcode varchar(50) not null,
   version int not null,
   modified timestamp not null,
   document clob not null,
   primary key(id)
);