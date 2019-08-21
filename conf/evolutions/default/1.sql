# --- !Ups
create table "comments"(
  "id"  int auto_increment primary key,
  "content" varchar(255) not null,
  "contributor_name" varchar(255) not null
);

insert into "comments" values (default, 'test_content', 'test_contributor_name');

# --- !Downs
drop table if exists "comments";
