DROP TABLE IF EXISTS AUTHORS;
DROP TABLE IF EXISTS BOOKS;
DROP TABLE IF EXISTS GENERIS;
DROP TABLE IF EXISTS COMMENTS;
DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS ROLES;

create table users(
    id bigserial,
    login varchar(255),
    password varchar(255),
    primary key (id)
);

create table roles(
    id bigserial,
    role varchar(255),
    primary key (id)
);

create table users_roles(
    user_id bigint references users (id),
    role_id bigint references roles(id),
    primary key (user_id, role_id)
);


create table books(
    id bigserial,
    name varchar(255),
    primary key (id)
);

create table comments(
    id bigserial,
    text varchar(500),
    date_time bigint,
    book_id bigint references books(id) on delete cascade,
    primary key (id)
);

create table authors(
    id bigserial,
    full_name varchar(511),
    primary key (id)
);

create table generis(
    id bigserial,
    title varchar(255),
    primary key (id)
);

create table books_authors(
    book_id bigint references books (id) on delete cascade,
    author_id bigint references authors(id),
    primary key (book_id, author_id)
);

create table books_generis(
    book_id bigint references books (id) on delete cascade,
    genre_id bigint references generis(id),
    primary key (book_id, genre_id)
);

--ACL--
--CREATE TABLE IF NOT EXISTS acl_sid (
--  id bigint(20) NOT NULL AUTO_INCREMENT,
--  principal tinyint(1) NOT NULL,
--  sid varchar(100) NOT NULL,
--  PRIMARY KEY (id),
--  UNIQUE KEY unique_uk_1 (sid,principal)
--);
--
--CREATE TABLE IF NOT EXISTS acl_class (
--  id bigint(20) NOT NULL AUTO_INCREMENT,
--  class varchar(255) NOT NULL,
--  PRIMARY KEY (id),
--  UNIQUE KEY unique_uk_2 (class)
--);
--
--CREATE TABLE IF NOT EXISTS acl_entry (
--  id bigint(20) NOT NULL AUTO_INCREMENT,
--  acl_object_identity bigint(20) NOT NULL,
--  ace_order int(11) NOT NULL,
--  sid bigint(20) NOT NULL,
--  mask int(11) NOT NULL,
--  granting tinyint(1) NOT NULL,
--  audit_success tinyint(1) NOT NULL,
--  audit_failure tinyint(1) NOT NULL,
--  PRIMARY KEY (id),
--  UNIQUE KEY unique_uk_4 (acl_object_identity,ace_order)
--);
--
--CREATE TABLE IF NOT EXISTS acl_object_identity (
--  id bigint(20) NOT NULL AUTO_INCREMENT,
--  object_id_class bigint(20) NOT NULL,
--  object_id_identity bigint(20) NOT NULL,
--  parent_object bigint(20) DEFAULT NULL,
--  owner_sid bigint(20) DEFAULT NULL,
--  entries_inheriting tinyint(1) NOT NULL,
--  PRIMARY KEY (id),
--  UNIQUE KEY unique_uk_3 (object_id_class,object_id_identity)
--);
--
--ALTER TABLE acl_entry
--ADD FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity(id);
--
--ALTER TABLE acl_entry
--ADD FOREIGN KEY (sid) REFERENCES acl_sid(id);
--
----
---- Constraints for table acl_object_identity
----
--ALTER TABLE acl_object_identity
--ADD FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id);
--
--ALTER TABLE acl_object_identity
--ADD FOREIGN KEY (object_id_class) REFERENCES acl_class (id);
--
--ALTER TABLE acl_object_identity
--ADD FOREIGN KEY (owner_sid) REFERENCES acl_sid (id);
