# Talk feedback schema

# --- !Ups

create table talk_feedbacks(
  id serial primary key,
  talk_id integer references talks(id),
  positive boolean not null,
  details text
);

# --- !Downs

drop table talk_feedbacks;
