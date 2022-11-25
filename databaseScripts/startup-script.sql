DROP TABLE IF EXISTS answers cascade;
DROP TABLE IF EXISTS categories CASCADE ;
DROP TABLE IF EXISTS difficulty_levels CASCADE;
DROP TABLE IF EXISTS favourites CASCADE;
DROP TABLE IF EXISTS "Friends Requests" CASCADE;
DROP TABLE IF EXISTS questions CASCADE;
DROP TABLE IF EXISTS quizes CASCADE;
DROP TABLE IF EXISTS ranks cascade;
DROP TABLE IF EXISTS "Solved Quizes" CASCADE;
DROP TABLE IF EXISTS "User Settings" CASCADE;
DROP TABLE IF EXISTS users CASCADE;

DROP SEQUENCE IF EXISTS "Difficulty Levels_Difficulty L";

CREATE TABLE answers (
    id                  INTEGER NOT NULL,
    content             VARCHAR(200) NOT NULL,
    is_correct          bool,
    questions_quizes_id INTEGER NOT NULL
);

CREATE TABLE categories (
    name        VARCHAR(50) NOT NULL,
    description VARCHAR(200)
);

ALTER TABLE categories ADD CONSTRAINT categories_pk PRIMARY KEY ( name );

CREATE TABLE Difficulty_Levels (
    name                   VARCHAR(100),
    stars                  INTEGER NOT NULL,
    description            VARCHAR(200),
    Difficulty_Levels_ID INTEGER NOT NULL
);

ALTER TABLE Difficulty_Levels ADD CONSTRAINT Difficulty_Levels_PK PRIMARY KEY ( Difficulty_Levels_ID );

CREATE TABLE favourites (
    user_id   INTEGER NOT NULL,
    quiz_id   INTEGER NOT NULL,
    quizes_id INTEGER NOT NULL,
    users_id  INTEGER NOT NULL
);

ALTER TABLE favourites
    ADD CONSTRAINT favourites_pk PRIMARY KEY ( user_id,
                                               quiz_id,
                                               quizes_id,
                                               users_id );

CREATE TABLE "Friends Requests" (
    sent      DATE NOT NULL,
    from_user INTEGER NOT NULL,
    to_user   DATE NOT NULL,
    users_id  INTEGER NOT NULL,
    users_id2 INTEGER NOT NULL
);

ALTER TABLE "Friends Requests"
    ADD CONSTRAINT friends_requests_pk PRIMARY KEY ( sent,
                                                     from_user,
                                                     to_user,
                                                     users_id,
                                                     users_id2 );

CREATE TABLE questions (
    id               INTEGER NOT NULL,
    content          VARCHAR(200) NOT NULL,
    image            VARCHAR(100),
    creation_date    DATE NOT NULL,
    modiciation_date DATE,
    quizes_id        INTEGER NOT NULL
);

ALTER TABLE questions ADD CONSTRAINT questions_pk PRIMARY KEY ( quizes_id );

CREATE TABLE quizes (
    id                                       INTEGER NOT NULL,
    name                                     VARCHAR(100) NOT NULL,
    description                              VARCHAR(200),
    image                                    VARCHAR(100),
    points                                   INTEGER NOT NULL,
    likes                                    INTEGER,
    creation_date                            DATE NOT NULL,
    modification_date                        DATE,
    privacy_settings                         VARCHAR,
    categories_name                          VARCHAR(50) NOT NULL,
    Difficulty_Levels_Difficulty_Levels_ID INTEGER NOT NULL
);

ALTER TABLE quizes ADD CONSTRAINT quizes_pk PRIMARY KEY ( id );

CREATE TABLE ranks (
    min_points INTEGER NOT NULL,
    max_pints  INTEGER NOT NULL,
    name       VARCHAR(100)
);

ALTER TABLE ranks ADD CONSTRAINT ranks_pk PRIMARY KEY ( min_points,
                                                        max_pints );

CREATE TABLE "Solved Quizes" (
    user_id   INTEGER NOT NULL,
    "date"    DATE NOT NULL,
    quiz_id   INTEGER NOT NULL,
    quizes_id INTEGER NOT NULL,
    users_id  INTEGER NOT NULL
);

ALTER TABLE "Solved Quizes"
    ADD CONSTRAINT solved_quizes_pk PRIMARY KEY ( user_id,
                                                  "date",
                                                  quiz_id,
                                                  quizes_id,
                                                  users_id );

CREATE TABLE users (
    id               INTEGER NOT NULL,
    username         VARCHAR(100) NOT NULL,
    email            VARCHAR(100) NOT NULL,
    name             VARCHAR(100) NOT NULL,
    avatar           VARCHAR,
    total_points     INTEGER,
    solved_quizes    INTEGER,
    created_quizes   INTEGER,
    ranks_min_points INTEGER NOT NULL,
    ranks_max_pints  INTEGER NOT NULL
);

ALTER TABLE users ADD CONSTRAINT users_pk PRIMARY KEY ( id );

CREATE TABLE "User Settings" (
    dark_mode          CHAR(1),
    preferred_language VARCHAR(4),
    users_id           INTEGER NOT NULL
);

ALTER TABLE "User Settings" ADD CONSTRAINT user_settings_pk PRIMARY KEY ( users_id );

ALTER TABLE answers
    ADD CONSTRAINT answers_questions_fk FOREIGN KEY ( questions_quizes_id )
        REFERENCES questions ( quizes_id )
            ON DELETE CASCADE;

ALTER TABLE favourites
    ADD CONSTRAINT favourites_quizes_fk FOREIGN KEY ( quizes_id )
        REFERENCES quizes ( id )
            ON DELETE CASCADE;

ALTER TABLE favourites
    ADD CONSTRAINT favourites_users_fk FOREIGN KEY ( users_id )
        REFERENCES users ( id )
            ON DELETE CASCADE;

ALTER TABLE "Friends Requests"
    ADD CONSTRAINT friends_requests_users_fk FOREIGN KEY ( users_id )
        REFERENCES users ( id )
            ON DELETE CASCADE;

ALTER TABLE "Friends Requests"
    ADD CONSTRAINT friends_requests_users_fkv1 FOREIGN KEY ( users_id2 )
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
        REFERENCES Difficulty_Levels ( Difficulty_Levels_ID );

ALTER TABLE "Solved Quizes"
    ADD CONSTRAINT solved_quizes_quizes_fk FOREIGN KEY ( quizes_id )
        REFERENCES quizes ( id )
            ON DELETE CASCADE;

ALTER TABLE "Solved Quizes"
    ADD CONSTRAINT solved_quizes_users_fk FOREIGN KEY ( users_id )
        REFERENCES users ( id )
            ON DELETE CASCADE;

ALTER TABLE "User Settings"
    ADD CONSTRAINT user_settings_users_fk FOREIGN KEY ( users_id )
        REFERENCES users ( id );

ALTER TABLE users
    ADD CONSTRAINT users_ranks_fk FOREIGN KEY ( ranks_min_points,
                                                ranks_max_pints )
        REFERENCES ranks ( min_points,
                           max_pints );

CREATE SEQUENCE "Difficulty Levels_Difficulty L" START WITH 1 increment by 1;