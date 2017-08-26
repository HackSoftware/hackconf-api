# Talks schema

# --- !Ups

create table talks(
  id serial primary key,
  title varchar(255) not null,
  description text not null,
  speaker_id integer references speakers(id)
);

# --- !Downs

drop table talks;
