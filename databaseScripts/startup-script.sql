DROP TABLE IF EXISTS answers cascade;
DROP TABLE IF EXISTS categories CASCADE ;
DROP TABLE IF EXISTS difficulty_levels CASCADE;
DROP TABLE IF EXISTS favourites CASCADE;
DROP TABLE IF EXISTS friends_requests CASCADE;
DROP TABLE IF EXISTS questions CASCADE;
DROP TABLE IF EXISTS quizes CASCADE;
DROP TABLE IF EXISTS ranks cascade;
DROP TABLE IF EXISTS solved_quizes CASCADE;
DROP TABLE IF EXISTS user_settings CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS users_passwords CASCADE;
DROP FUNCTION IF EXISTS create_user CASCADE;

CREATE TABLE answers (
                         id                  SERIAL8,
                         content             VARCHAR(400) NOT NULL,
                         is_correct          bool,
                         questions_quizes_id INTEGER NOT NULL
);

CREATE TABLE categories (
                            name        VARCHAR(100) NOT NULL,
                            description VARCHAR(400)
);

CREATE UNIQUE INDEX IF NOT EXISTS categories_index on categories(name);

CREATE TABLE difficulty_levels (
                                   name                   VARCHAR(100),
                                   stars                  INTEGER NOT NULL,
                                   description            VARCHAR(400),
                                   Difficulty_Levels_ID   SERIAL8
);

CREATE UNIQUE INDEX IF NOT EXISTS difficulty_levels_index on difficulty_levels(Difficulty_Levels_ID, name);

CREATE TABLE favourites (
                            user_id   INTEGER NOT NULL,
                            quiz_id   INTEGER NOT NULL
);

CREATE TABLE friends_requests (
                                  sent      DATE NOT NULL,
                                  from_user INTEGER NOT NULL,
                                  to_user   INTEGER NOT NULL,
                                  status varchar(100) NOT NULL
);

CREATE TABLE questions (
                           id               SERIAL8,
                           content          VARCHAR(400) NOT NULL,
                           image            VARCHAR(300),
                           creation_date    DATE NOT NULL,
                           modification_date DATE,
                           quizes_id        INTEGER NOT NULL
);

CREATE TABLE quizes (
                        id                                       SERIAL8,
                        name                                     VARCHAR(200) NOT NULL,
                        description                              VARCHAR(400),
                        image                                    VARCHAR(300),
                        points                                   INTEGER NOT NULL,
                        likes                                    INTEGER,
                        creation_date                            DATE NOT NULL,
                        modification_date                        DATE,
                        privacy_settings                         VARCHAR(100),
                        categories_name                          VARCHAR(100),
                        Difficulty_Levels_Difficulty_Levels_ID   INTEGER NOT NULL,
                        creator_user_id                           INTEGER NOT NULL
);

CREATE TABLE ranks (
                       min_points INTEGER NOT NULL,
                       max_points  INTEGER NOT NULL,
                       name       VARCHAR(200)
);

CREATE UNIQUE INDEX IF NOT EXISTS ranks_index on ranks(name);

CREATE TABLE solved_quizes (
                               user_id   INTEGER NOT NULL,
                               "date"    DATE NOT NULL,
                               quiz_id   INTEGER NOT NULL
);

CREATE TABLE users (
                       id               SERIAL8,
                       username         VARCHAR(200) NOT NULL,
                       email            VARCHAR(200) NOT NULL,
                       name             VARCHAR(200) NOT NULL,
                       avatar           VARCHAR,
                       total_points     INTEGER,
                       solved_quizes    INTEGER,
                       created_quizes   INTEGER,
                       rank             VARCHAR(200)
);

CREATE TABLE user_settings (
                               dark_mode          CHAR(1),
                               preferred_language VARCHAR(4),
                               users_id           INTEGER NOT NULL
);

CREATE TABLE users_passwords (
                                 id INTEGER NOT NULL,
                                 username VARCHAR(100) NOT NULL,
                                 password VARCHAR(100) NOT NULL,
                                 last_modified DATE
);

CREATE UNIQUE INDEX IF NOT EXISTS passwords_index on users_passwords(id, username, password);

ALTER TABLE categories ADD CONSTRAINT categories_pk PRIMARY KEY ( name );
ALTER TABLE users ADD CONSTRAINT users_pk PRIMARY KEY ( id );
ALTER TABLE users ADD CONSTRAINT users_uk unique ( username );
ALTER TABLE users_passwords ADD CONSTRAINT users_passwords_pk PRIMARY KEY ( id );
ALTER TABLE ranks ADD CONSTRAINT ranks_pk PRIMARY KEY ( name );
ALTER TABLE quizes ADD CONSTRAINT quizes_pk PRIMARY KEY ( id );
ALTER TABLE questions ADD CONSTRAINT questions_pk PRIMARY KEY ( id );
ALTER TABLE difficulty_levels ADD CONSTRAINT Difficulty_Levels_PK PRIMARY KEY ( Difficulty_Levels_ID );
ALTER TABLE favourites
    ADD CONSTRAINT favourites_pk PRIMARY KEY ( user_id,
                                               quiz_id
        );
ALTER TABLE friends_requests
    ADD CONSTRAINT friends_requests_users_pk PRIMARY KEY ( sent, from_user, to_user );
ALTER TABLE solved_quizes
    ADD CONSTRAINT solved_quizes_pk PRIMARY KEY ( user_id,
                                                  "date",
                                                  quiz_id
        );
ALTER TABLE user_settings ADD CONSTRAINT user_settings_pk PRIMARY KEY ( users_id );


ALTER TABLE answers
    ADD CONSTRAINT answers_questions_fk FOREIGN KEY ( questions_quizes_id )
        REFERENCES questions ( id )
        ON DELETE CASCADE;

ALTER TABLE favourites
    ADD CONSTRAINT favourites_quizes_fk FOREIGN KEY ( quiz_id )
        REFERENCES quizes ( id )
        ON DELETE CASCADE;

ALTER TABLE favourites
    ADD CONSTRAINT favourites_users_fk FOREIGN KEY ( user_id )
        REFERENCES users ( id )
        ON DELETE CASCADE;

ALTER TABLE friends_requests
    ADD CONSTRAINT friends_requests_users_fk FOREIGN KEY ( from_user )
        REFERENCES users ( id )
        ON DELETE CASCADE;

ALTER TABLE friends_requests
    ADD CONSTRAINT friends_requests_users_fkv1 FOREIGN KEY ( to_user )
        REFERENCES users ( id )
        ON DELETE CASCADE;

ALTER TABLE questions
    ADD CONSTRAINT questions_quizes_fk FOREIGN KEY ( quizes_id )
        REFERENCES quizes ( id )
        ON DELETE CASCADE;

ALTER TABLE quizes
    ADD CONSTRAINT quizes_categories_fk FOREIGN KEY ( categories_name )
        REFERENCES categories ( name ) on delete set null;

ALTER TABLE quizes
    ADD CONSTRAINT quizes_difficulty_levels_fk FOREIGN KEY ( Difficulty_Levels_Difficulty_Levels_ID )
        REFERENCES difficulty_levels ( Difficulty_Levels_ID ) on delete set null;

ALTER TABLE solved_quizes
    ADD CONSTRAINT solved_quizes_quizes_fk FOREIGN KEY ( quiz_id )
        REFERENCES quizes ( id )
        ON DELETE CASCADE;

ALTER TABLE solved_quizes
    ADD CONSTRAINT solved_quizes_users_fk FOREIGN KEY ( user_id )
        REFERENCES users ( id )
        ON DELETE CASCADE;

ALTER TABLE user_settings
    ADD CONSTRAINT user_settings_users_fk FOREIGN KEY ( users_id )
        REFERENCES users ( id ) on delete cascade ;

ALTER TABLE users
    ADD CONSTRAINT users_ranks_fk FOREIGN KEY ( rank )
        REFERENCES ranks ( name ) on delete set null;

ALTER TABLE users_passwords ADD CONSTRAINT users_passwords_fk FOREIGN KEY ( username ) references  users(username) on delete cascade ;
ALTER TABLE users_passwords ADD CONSTRAINT users_passwords_fk2 FOREIGN KEY ( id ) references  users(id) on delete cascade;


ALTER TABLE quizes ADD CONSTRAINT quizes_creator_fk FOREIGN KEY ( creator_user_id ) references users(id) on delete cascade;

CREATE OR REPLACE FUNCTION calculate_global_ranks() returns trigger as $calculate_global_ranks$
    DECLARE
        all_users_count integer;
        twentyPercentOfUsers float(2);
        thirtyPercentOfUsers float(2);
        cUsers CURSOR FOR
            SELECT *
            FROM users
            ORDER BY total_points DESC
                FOR UPDATE;
        vCnt integer := 0;
    BEGIN
    select count(*) into all_users_count from users;
    twentyPercentOfUsers := all_users_count::float * 20 / 100;
    thirtyPercentOfUsers := all_users_count::float * 30 / 100;

    FOR vUser IN cUsers LOOP
        IF vCnt < twentyPercentOfUsers then
            UPDATE users
            set rank = 'GOLD'
            where current of cUsers;
        END IF;
        IF vCnt > twentyPercentOfUsers and vCnt <= twentyPercentOfUsers + thirtyPercentOfUsers then
            UPDATE users
            set rank = 'SILVER'
            where current of cUsers;
        END IF;
        IF vCnt > twentyPercentOfUsers + thirtyPercentOfUsers then
            UPDATE users
            set rank = 'BRONZ'
            where current of cUsers;
        END IF;
         vCnt := vCnt + 1;
    END LOOP;
        UPDATE ranks
            set max_points = (select max(total_points) from users)
            where name = 'GOLD';
        UPDATE ranks
            set min_points = (select max(total_points) + 1 from users where rank = 'SILVER')
            where name = 'GOLD';
        UPDATE ranks
            set max_points = (select max(total_points) from users where rank = 'SILVER')
            where name = 'SILVER';
        UPDATE ranks
            set min_points = (select max(total_points) + 1 from users where rank = 'BRONZ')
            where name = 'BRONZ';
        UPDATE ranks
            set max_points = (select max(total_points) from users where rank = 'BRONZ')
            where name = 'BRONZ';
        UPDATE ranks
            set min_points = (select min(total_points) + 1 from users)
            where name = 'BRONZ';
    return null;
    END;
    $calculate_global_ranks$ LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER calculate_ranks
    AFTER
	INSERT OR TRUNCATE OR UPDATE OF total_points
                          ON users
                              EXECUTE FUNCTION calculate_global_ranks();

CREATE OR REPLACE FUNCTION create_user(
    vusername IN        VARCHAR(200),
    vpassword IN       VARCHAR(200),
    vemail    IN       VARCHAR(200),
    vname     IN        VARCHAR(200),
    vavatar   IN        VARCHAR(300)
) returns RECORD language plpgsql
    as $$
        DECLARE
            vid users.id%TYPE;
            vUserPass Record;
    BEGIN
        INSERT INTO users(username, email, name, avatar, total_points, solved_quizes, created_quizes, rank)
            values (
                   vusername,
                   vemail,
                   vname,
                   vavatar,
                   0,
                   0,
                   0,
                   'BRONZ'
               ) returning users.id into vid;

        insert into user_settings(dark_mode, preferred_language, users_id)
            values (
                   'N', 'EN', vid
               );

        insert into users_passwords(id, username, password, last_modified)
            values (
                   vid, (select users.username from users where users.id = vid), vpassword, current_date
               );

        select (up.id, up.username, up.password, up.last_modified) from users_passwords up into vUserPass where up.id = vid;
        return vUserPass;
END; $$

CREATE OR REPLACE FUNCTION update_points_and_quizzes() returns trigger as $update_points_and_quizzes$
    BEGIN
    UPDATE users us
        set total_points = (select sum(q.points)
                            from users u join solved_quizes s on u.id = s.user_id
                                join quizes q on s.quiz_id = q.id
                                group by u.id
                                having u.id = us.id);

    UPDATE users us
        set solved_quizes = (select count(q.id)
                                from users u join solved_quizes s on u.id = s.user_id
                                join quizes q on s.quiz_id = q.id
                                group by u.id
                                having u.id = us.id);
    UPDATE users us
        set created_quizes = (select count(q.creator_user_id) from users u
                                join quizes q on q.creator_user_id = u.id
                                group by u.id
                                having u.id = us.id);
    return null;
    END;
    $update_points_and_quizzes$ LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER calculate_points_and_quizzes
    AFTER
	INSERT OR TRUNCATE OR UPDATE
    ON quizes
    EXECUTE FUNCTION update_points_and_quizzes();
