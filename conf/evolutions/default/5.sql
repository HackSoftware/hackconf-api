# Livestreams schema

# --- !Ups

create table live_streams(
  id serial primary key,
  url text
);

# --- !Downs

drop table live_streams;
