# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table student (
  id                        bigint not null,
  username                  varchar(255),
  password                  varchar(255),
  ssn                       varchar(255),
  first_name                varchar(255),
  surname                   varchar(255),
  street_adress             varchar(255),
  zipcode                   varchar(255),
  city                      varchar(255),
  email                     varchar(255),
  constraint pk_student primary key (id))
;

create sequence student_seq;




# --- !Downs

drop table if exists student cascade;

drop sequence if exists student_seq;

