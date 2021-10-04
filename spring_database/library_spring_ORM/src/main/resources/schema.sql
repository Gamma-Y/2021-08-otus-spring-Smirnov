DROP TABLE IF EXISTS AUTHORS;
DROP TABLE IF EXISTS BOOKS;
DROP TABLE IF EXISTS GENERIS;
DROP TABLE IF EXISTS COMMENTS;


create table books(
    id bigserial,
    name varchar(255),
    primary key (id)
);

create table comments(
    id bigserial,
    text varchar(1000),
    book_id bigint references books(id) on delete cascade,
    primary key (id)
);

create table authors(
    id bigserial,
    name varchar(255),
    surname varchar(255),
    middle_name varchar(255),
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
