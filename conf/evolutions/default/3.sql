# Talk schedules schema

# --- !Ups

create table talk_schedules(
  id serial primary key,
  talk_id integer references talks(id),
  start_date date not null,
  start_time time not null,
  end_date date not null,
  end_time time not null,
  delay integer not null,
  canceled boolean not null
);

# --- !Downs

drop table talk_schedules;
