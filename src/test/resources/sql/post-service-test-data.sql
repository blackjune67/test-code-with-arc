insert into `users` (`id`,
                     `email`,
                     `nickname`,
                     `address`,
                     `certification_code`,
                     `status`,
                     `last_login_at`)
values (1,
        'buea486@gmail.com',
        'june67',
        'Seoul',
        'aaaaaa-aa-aaaaa-aaaaa-aaaaaa',
        'ACTIVE',
        0);

insert into `users` (`id`,
                     `email`,
                     `nickname`,
                     `address`,
                     `certification_code`,
                     `status`,
                     `last_login_at`)
values (2,
        'buea496@gmail.com',
        'june68',
        'Seoul',
        'aaaaaa-aa-aaaaa-aaaaa-aaaaaa',
        'PENDING',
        0);

insert into `posts` (`id`, `content`, `created_at`, `modified_at`, `user_id`) values (1, 'testpost', 2024072510133223, 0, 1);