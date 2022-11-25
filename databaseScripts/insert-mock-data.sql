INSERT INTO ranks (min_points, max_pints, name)
    values (
            1,
            100,
            'BRONZ'
           );
INSERT INTO ranks (min_points, max_pints, name)
    values (
            101,
            500,
            'SILVER'
           );
INSERT INTO ranks (min_points, max_pints, name)
    values (
            501,
            1000,
            'GOLD'
           );

INSERT INTO users(id, username, email, name, avatar, total_points, solved_quizes, created_quizes, ranks_min_points, ranks_max_pints)
    values (1,
            'Mochal',
            'michal@test@gmail.com',
            'Michał',
            null,
            100,
            5,
            2,
            1,
            100
            );
INSERT INTO users(id, username, email, name, avatar, total_points, solved_quizes, created_quizes, ranks_min_points, ranks_max_pints)
    values (2,
            'Klaudia',
            'klaudia@test@gmail.com',
            'Klaudia',
            null,
            2,
            1,
            9,
            501,
            1000
            );
INSERT INTO users(id, username, email, name, avatar, total_points, solved_quizes, created_quizes, ranks_min_points, ranks_max_pints)
    values (3,
            'Test',
            'test@test@gmail.com',
            'Test',
            null,
            100,
            5,
            2,
            1,
            100
            );

insert into "User Settings"(dark_mode, preferred_language, users_id)
    values (
            'Y', 'PL', 1
           );