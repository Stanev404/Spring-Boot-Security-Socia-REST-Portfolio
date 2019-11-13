-- drop table USER_ROLE;
-- drop table APP_ROLE;
-- drop table APP_USER;
-- DROP TABLE user_role;
-- DROP TABLE verification_token;
-- DROP TABLE app_user;
-- DROP TABLE app_role;
-- DROP TABLE persistent_logins;
-- Create table
create table if not exists APP_USER
(
  USER_ID           BIGINT not null,
  NAME          VARCHAR(36) not null,
  EMAIL         VARCHAR(36) not null,
  ENCRYTED_PASSWORD VARCHAR(128) not null,
  ENABLED           int not null,
  primary key(USER_ID),
  unique (EMAIL)
) ;
--
-- alter table APP_USER
--   add constraint APP_USER_PK primary key (USER_ID);
--
-- alter table APP_USER
--   add constraint APP_USER_UK unique (EMAIL);
 
 
-- Create table
create table if not exists APP_ROLE
(
  ROLE_ID   BIGINT not null,
  ROLE_NAME VARCHAR(30) not null,
  primary key(ROLE_ID),
  unique (ROLE_NAME)
) ;
--  
-- alter table APP_ROLE
--   add constraint APP_ROLE_PK primary key (ROLE_ID);
--
-- alter table APP_ROLE
--   add constraint APP_ROLE_UK unique (ROLE_NAME);
 
 
-- Create table
create table if not exists USER_ROLE
(
  ID      BIGINT not null,
  USER_ID BIGINT not null,
  ROLE_ID BIGINT not null,
  primary key(ID),
  unique (USER_ID,ROLE_ID),
  foreign key (USER_ID) REFERENCES APP_USER(USER_ID),
  foreign key (ROLE_ID) REFERENCES APP_ROLE(ROLE_ID)
);

-- CREATE SEQUENCE hibernate_sequence START 1;

-- create table if not exists comments
-- (
--   ID    BIGINT not null,
--   PUBLISHER_NAME VARCHAR(30) not null,
--   CONTENT_OF_COMMENT VARCHAR(30) not null,
--   primary key(ID);
-- );


--  
-- alter table USER_ROLE
--   add constraint USER_ROLE_PK primary key (ID);
--
-- alter table USER_ROLE
--   add constraint USER_ROLE_UK unique (USER_ID, ROLE_ID);
--
-- alter table USER_ROLE
--   add constraint USER_ROLE_FK1 foreign key (USER_ID)
--   references APP_USER (USER_ID);
--
-- alter table USER_ROLE
--   add constraint USER_ROLE_FK2 foreign key (ROLE_ID)
--   references APP_ROLE (ROLE_ID);

-- Create table
create table if not exists VERIFICATION_TOKEN
(
  TOKEN_ID           BIGINT not null,
  TOKEN          VARCHAR(36) not null,
  USER_ID         BIGINT not null,
  primary key (TOKEN_ID),
  unique (TOKEN,USER_ID),
  foreign key(USER_ID) references APP_USER(USER_ID)
) ;
--
-- create table if not exists comments
-- (
--   ID           BIGINT not null,
--   PUBLISHERNAME          VARCHAR(36) not null,
--   CONTENTOFCOMMENT         VARCHAR(36) not null,
--   primary key (ID)
-- ) ;
--
-- alter table VERIFICATION_TOKEN
--   add constraint VERIFICATION_TOKEN_PK primary key (TOKEN_ID);
--
-- alter table VERIFICATION_TOKEN
--   add constraint VERIFICATION_TOKEN_UK unique (TOKEN, USER_ID);
--
-- alter table VERIFICATION_TOKEN
--   add constraint VERIFICATION_TOKEN_FK1 foreign key (USER_ID)
--   references APP_USER (USER_ID);
 
 
-- Used by Spring Remember Me API.  
-- CREATE TABLE if not exists Persistent_Logins (
--
--     username varchar(64) not null,
--     series varchar(64) not null,
--     token varchar(64) not null,
--     last_used timestamp not null,
--     PRIMARY KEY (series)
--
-- );
 
--------------------------------------
