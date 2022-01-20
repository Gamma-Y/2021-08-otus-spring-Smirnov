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
