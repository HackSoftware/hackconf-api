# Talk feedback schema

# --- !Ups

create table talk_feedbacks(
  id serial primary key,
  talk_id integer references talks(id),
  device_id text not null,
  feedback varchar(32) not null,
  created_at timestamp default current_timestamp,
  details text
);

# --- !Downs

drop table talk_feedbacks;
