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

DROP SEQUENCE IF EXISTS "Difficulty Levels_Difficulty L";

CREATE TABLE answers (
    id                  SERIAL8,
    content             VARCHAR(200) NOT NULL,
    is_correct          bool,
    questions_quizes_id INTEGER NOT NULL
);

CREATE TABLE categories (
    name        VARCHAR(50) NOT NULL,
    description VARCHAR(200)
);

ALTER TABLE categories ADD CONSTRAINT categories_pk PRIMARY KEY ( name );

CREATE TABLE difficulty_levels (
    name                   VARCHAR(100),
    stars                  INTEGER NOT NULL,
    description            VARCHAR(200),
    Difficulty_Levels_ID INTEGER NOT NULL
);

ALTER TABLE difficulty_levels ADD CONSTRAINT Difficulty_Levels_PK PRIMARY KEY ( Difficulty_Levels_ID );

CREATE TABLE favourites (
    user_id   INTEGER NOT NULL,
    quiz_id   INTEGER NOT NULL
);

ALTER TABLE favourites
    ADD CONSTRAINT favourites_pk PRIMARY KEY ( user_id,
                                               quiz_id
                                                );

CREATE TABLE friends_requests (
    sent      DATE NOT NULL,
    from_user INTEGER NOT NULL,
    to_user   INTEGER NOT NULL
);

ALTER TABLE friends_requests
    ADD CONSTRAINT friends_requests_pk PRIMARY KEY ( sent,
                                                     from_user,
                                                     to_user
                                                     );

CREATE TABLE questions (
    id               SERIAL8,
    content          VARCHAR(200) NOT NULL,
    image            VARCHAR(100),
    creation_date    DATE NOT NULL,
    modiciation_date DATE,
    quizes_id        INTEGER NOT NULL
);

ALTER TABLE questions ADD CONSTRAINT questions_pk PRIMARY KEY ( id );

CREATE TABLE quizes (
    id                                       SERIAL8,
    name                                     VARCHAR(100) NOT NULL,
    description                              VARCHAR(200),
    image                                    VARCHAR(100),
    points                                   INTEGER NOT NULL,
    likes                                    INTEGER,
    creation_date                            DATE NOT NULL,
    modification_date                        DATE,
    privacy_settings                         VARCHAR(50),
    categories_name                          VARCHAR(50) NOT NULL,
    Difficulty_Levels_Difficulty_Levels_ID INTEGER NOT NULL,
    creator_user_id                           INTEGER NOT NULL
);

ALTER TABLE quizes ADD CONSTRAINT quizes_pk PRIMARY KEY ( id );

CREATE TABLE ranks (
    min_points INTEGER NOT NULL,
    max_points  INTEGER NOT NULL,
    name       VARCHAR(100)
);

ALTER TABLE ranks ADD CONSTRAINT ranks_pk PRIMARY KEY ( name );

CREATE TABLE solved_quizes (
    user_id   INTEGER NOT NULL,
    "date"    DATE NOT NULL,
    quiz_id   INTEGER NOT NULL
);

ALTER TABLE solved_quizes
    ADD CONSTRAINT solved_quizes_pk PRIMARY KEY ( user_id,
                                                  "date",
                                                  quiz_id
                                                );

CREATE TABLE users (
    id               SERIAL8,
    username         VARCHAR(100) NOT NULL,
    email            VARCHAR(100) NOT NULL,
    name             VARCHAR(100) NOT NULL,
    avatar           VARCHAR,
    total_points     INTEGER,
    solved_quizes    INTEGER,
    created_quizes   INTEGER,
    rank             VARCHAR(100)
);

ALTER TABLE users ADD CONSTRAINT users_pk PRIMARY KEY ( id );

CREATE TABLE user_settings (
    dark_mode          CHAR(1),
    preferred_language VARCHAR(4),
    users_id           INTEGER NOT NULL
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
        REFERENCES categories ( name );

ALTER TABLE quizes
    ADD CONSTRAINT quizes_difficulty_levels_fk FOREIGN KEY ( Difficulty_Levels_Difficulty_Levels_ID )
        REFERENCES difficulty_levels ( Difficulty_Levels_ID );

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
        REFERENCES users ( id );

ALTER TABLE users
    ADD CONSTRAINT users_ranks_fk FOREIGN KEY ( rank )
        REFERENCES ranks ( name );

ALTER TABLE quizes ADD CONSTRAINT quizes_creator_fk FOREIGN KEY ( creator_user_id ) references users(id);

CREATE SEQUENCE "Difficulty Levels_Difficulty L" START WITH 1 increment by 1;