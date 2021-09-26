insert into generis (id, `name`) values (1, 'Программирование');
insert into generis (id, `name`) values (2, 'Финансы');


insert into authors (id, `name`, `surname`, `middlename`) values (1, 'Кэтти,', 'Сьерра', '');
insert into authors (id, `name`, `surname`, `middlename`) values (2, 'Роберт', 'Мартин', 'Сесил ');
insert into authors (id, `name`, `surname`, `middlename`) values (3, 'Клейсон', 'Джорж', 'Самюэль');


insert into books (id, `name`, `genre_id`, `author_id`) values (1, 'Head First Java, Изучаем Java', '1', '1');
insert into books (id, `name`, `genre_id`, `author_id`) values (2, 'Чистый код. Создание, анализ и рефакторинг', '1', '2');
insert into books (id, `name`, `genre_id`, `author_id`) values (3, 'Самый богатый человек в Вавилоне', '2', '3');
