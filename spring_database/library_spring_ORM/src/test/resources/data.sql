insert into generis (id, `title`)
values (1, 'test');

insert into authors (id, `full_name`)
values (1, 'test1'), (2, 'test2');

insert into books (id, `name`)
    values (1, 'test');

insert into comments (id, `text`, date_time, book_id)
values (1, 'test1', 1545, 1), (2, 'test2', 16876786, 1);

insert into books_authors (book_id, author_id)
    values (1, 1), (1, 2);

insert into books_generis (book_id, genre_id)
    values (1, 1);