--insert into books (id, `name`)
--    values (1, 'Head First Java, Изучаем Java'),
--           (2, 'Чистый код. Создание, анализ и рефакторинг'),
--           (3, 'Самый богатый человек в Вавилоне');
--
--insert into generis (id, `title`)
--values (1, 'Программирование'), (2, 'Финансы');

insert into authors (id, `name`, `surname`, `middle_name`)
values (1, 'Кэтти', 'Сьерра', ''), (2, 'Роберт', 'Мартин', 'Сесил '), (3, 'Клейсон', 'Джорж', 'Самюэль');


--insert into comments (id, `text`, date_time, book_id)
--values (1, 'comment1', 1545, 1), (2, 'comment1_2', 16876786, 1), (3, 'comment1_3', 16546456, 2);
----       (4, 'comment2', 1545424, 2), (5, 'comment3', 16546456, 3), (6, 'comment3_1', 15646456, 3)
--
--
--insert into books_authors (book_id, author_id)
--    values (1, 1), (1, 2), (2, 2), (3, 3), (3, 1);
--
--insert into books_generis (book_id, genre_id)
--    values (1, 1), (2, 1), (3, 2), (1, 2);
