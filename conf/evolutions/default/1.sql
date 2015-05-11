# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table activity (
  id                        bigint not null,
  student_id                bigint not null,
  text                      varchar(255),
  date                      varchar(255),
  constraint pk_activity primary key (id))
;

create table assignment (
  id                        bigint not null,
  course_id                 bigint not null,
  name                      varchar(255),
  points                    varchar(255),
  grade                     varchar(255),
  constraint pk_assignment primary key (id))
;

create table course (
  id                        bigint not null,
  program_id                bigint not null,
  name                      varchar(255),
  code                      varchar(255),
  points                    varchar(255),
  grade                     varchar(255),
  rate                      varchar(255),
  status                    varchar(255),
  first_date_to_register    varchar(255),
  last_date_to_register     varchar(255),
  start_date                varchar(255),
  end_date                  varchar(255),
  constraint pk_course primary key (id))
;

create table examination (
  id                        bigint not null,
  course_id                 bigint not null,
  name                      varchar(255),
  points                    varchar(255),
  grade                     varchar(255),
  status                    varchar(255),
  examination_date          varchar(255),
  first_date_to_register    varchar(255),
  last_date_to_register     varchar(255),
  time_start                varchar(255),
  time_end                  varchar(255),
  local                     varchar(255),
  constraint pk_examination primary key (id))
;

create table program (
  id                        bigint not null,
  student_id                bigint not null,
  name                      varchar(255),
  code                      varchar(255),
  points                    varchar(255),
  rate                      varchar(255),
  constraint pk_program primary key (id))
;

create table student (
  id                        bigint not null,
  username                  varchar(255),
  password                  varchar(255),
  ssn                       varchar(255),
  first_name                varchar(255),
  surname                   varchar(255),
  co                        varchar(255),
  street_adress             varchar(255),
  zipcode                   varchar(255),
  city                      varchar(255),
  email                     varchar(255),
  notify_by_email           boolean,
  constraint pk_student primary key (id))
;

create sequence activity_seq;

create sequence assignment_seq;

create sequence course_seq;

create sequence examination_seq;

create sequence program_seq;

create sequence student_seq;

alter table activity add constraint fk_activity_student_1 foreign key (student_id) references student (id);
create index ix_activity_student_1 on activity (student_id);
alter table assignment add constraint fk_assignment_course_2 foreign key (course_id) references course (id);
create index ix_assignment_course_2 on assignment (course_id);
alter table course add constraint fk_course_program_3 foreign key (program_id) references program (id);
create index ix_course_program_3 on course (program_id);
alter table examination add constraint fk_examination_course_4 foreign key (course_id) references course (id);
create index ix_examination_course_4 on examination (course_id);
alter table program add constraint fk_program_student_5 foreign key (student_id) references student (id);
create index ix_program_student_5 on program (student_id);



# --- !Downs

drop table if exists activity cascade;

drop table if exists assignment cascade;

drop table if exists course cascade;

drop table if exists examination cascade;

drop table if exists program cascade;

drop table if exists student cascade;

drop sequence if exists activity_seq;

drop sequence if exists assignment_seq;

drop sequence if exists course_seq;

drop sequence if exists examination_seq;

drop sequence if exists program_seq;

drop sequence if exists student_seq;

