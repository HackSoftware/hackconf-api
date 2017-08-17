# Speakers schema

# --- !Ups

create table speakers(
  id serial primary key,
  first_name varchar(255) not null,
  last_name varchar(255) not null,
  bio text not null,
  url varchar(255) not null
);

# --- !Downs

drop table speakers;
