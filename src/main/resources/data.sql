create table floorplan (id bigint not null, creation_date timestamp not null, large varchar(255), name varchar(255), original varchar(255), thumb_nail varchar(255), last_update_date timestamp not null, project_id bigint, primary key (id))
create table project (id bigint not null, creation_date timestamp not null, name varchar(255), last_update_date timestamp not null, primary key (id))
create sequence hibernate_sequence start with 1 increment by 1