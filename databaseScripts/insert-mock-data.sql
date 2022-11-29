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
insert into "User Settings"(dark_mode, preferred_language, users_id)
    values (
            'Y', 'PL', 2
           );
insert into "User Settings"(dark_mode, preferred_language, users_id)
    values (
            'Y', 'EN', 3
           );

insert into  categories (name, description)
    values (
            'Zwierzęta', 'Kategoria zwierzęta'
           );
insert into  categories (name, description)
    values (
            'Sport', 'Kategoria sport'
           );
insert into  categories (name, description)
    values (
            'Polityka', 'Kategoria polityka'
           );

insert into difficulty_levels (name, stars, description, difficulty_levels_id)
    values (
            'Łatwy',
            1,
            'Dla początkujących',
            1
           );
insert into difficulty_levels (name, stars, description, difficulty_levels_id)
    values (
            'Średni',
            3,
            'Dla tych co już coś potrafią',
            2
           );
insert into difficulty_levels (name, stars, description, difficulty_levels_id)
    values (
            'Trudny',
            5,
            'Dla zaawansowanych',
            3
           );

insert into quizes(id, name, description, image, points, likes, creation_date, modification_date, privacy_settings, categories_name, difficulty_levels_difficulty_levels_id)
    values (
            1,
            'Quiz testowy',
            'Testowy quiz',
            null,
            10,
            0,
            current_date,
            current_date,
            'PUBLIC',
            'Sport',
            1
           );
insert into questions(id, content, image, creation_date, modiciation_date, quizes_id)
    values (
            1,
            'Czy to jest pierwsze testowe pytanie?',
            null,
            current_date,
            current_date,
            1
           );
insert into answers(id, content, is_correct, questions_quizes_id)
    values (
            1,
            'Tak',
            true,
            1
           );
insert into answers(id, content, is_correct, questions_quizes_id)
    values (
            2,
            'Nie',
            false,
            1
           );
insert into questions(id, content, image, creation_date, modiciation_date, quizes_id)
    values (
            2,
            'Czy to jest pierwsze testowe pytanie?',
            null,
            current_date,
            current_date,
            1
           );
insert into answers(id, content, is_correct, questions_quizes_id)
    values (
            3,
            'Nie',
            true,
            2
           );
insert into answers(id, content, is_correct, questions_quizes_id)
    values (
            4,
            'Tak',
            false,
            2
           );

insert into quizes(id, name, description, image, points, likes, creation_date, modification_date, privacy_settings, categories_name, difficulty_levels_difficulty_levels_id)
    values (
            2,
            'Quiz o zwieretach',
            'Testowy quiz o zwierzetach',
            null,
            100,
            10,
            current_date,
            current_date,
            'PUBLIC',
            'Zwierzęta',
            3
           );
insert into questions(id, content, image, creation_date, modiciation_date, quizes_id)
    values (
            3,
            'Pytanie 1',
            null,
            current_date,
            current_date,
            1
           );
insert into answers(id, content, is_correct, questions_quizes_id)
    values (
            6,
            'Odpowiedź 1',
            false,
            3
           );
insert into answers(id, content, is_correct, questions_quizes_id)
    values (
            7,
            'Odpowiedz 2',
            false,
            3
           );
insert into answers(id, content, is_correct, questions_quizes_id)
    values (
            8,
            'Odpowiedź 3',
            false,
            3
           );
insert into answers(id, content, is_correct, questions_quizes_id)
    values (
            7,
            'Odpowiedz 4',
            false,
            3
           );
insert into questions(id, content, image, creation_date, modiciation_date, quizes_id)
    values (
            4,
            'Drugie pytanie',
            null,
            current_date,
            current_date,
            2
           );
insert into answers(id, content, is_correct, questions_quizes_id)
    values (
            9,
            'Nie',
            true,
            4
           );
insert into answers(id, content, is_correct, questions_quizes_id)
    values (
            10,
            'Tak',
            false,
            4
           );
insert into answers(id, content, is_correct, questions_quizes_id)
    values (
            11,
            'Nie',
            true,
            4
           );
insert into answers(id, content, is_correct, questions_quizes_id)
    values (
            12,
            'Tak',
            false,
            4
           );

insert into favourites(user_id, quiz_id)
    values (
            1, 1
           );
insert into favourites(user_id, quiz_id)
    values (
            2, 2
           );
insert into favourites(user_id, quiz_id)
    values (
            3, 2
           );

insert into "Solved Quizes" (user_id, date, quiz_id)
    values (
            1, CURRENT_DATE, 1
           );

INSERT INTO "Friends Requests" (sent, from_user, to_user)
    VALUES (
            CURRENT_DATE,
            1,
            2
           );