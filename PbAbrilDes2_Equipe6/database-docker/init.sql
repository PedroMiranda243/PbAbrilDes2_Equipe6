CREATE DATABASE spring_database;

CREATE TABLE register (
                          id BIGINT GENERATED BY DEFAULT AS IDENTITY,
                          birthdate DATE,
                          created_at TIMESTAMP,
                          email VARCHAR(255),
                          first_name VARCHAR(255),
                          last_name VARCHAR(255),
                          password VARCHAR(255),
                          role VARCHAR(25) NOT NULL CHECK (role IN ('ROLE_ADMIN','ROLE_USUARIO')),
                          summary VARCHAR(255),
                          updated_at TIMESTAMP,
                          username VARCHAR(255),
                          PRIMARY KEY (id)
);

CREATE TABLE users (
                       id VARCHAR(255) PRIMARY KEY,
                       fullName VARCHAR(255),
                       username VARCHAR(255),
                       summary TEXT,
                       createdAt DATETIME,
                       updatedAt DATETIME
);

CREATE TABLE followers (
                           userId VARCHAR(255),
                           followerId VARCHAR(255),
                           PRIMARY KEY (userId, followerId),
                           FOREIGN KEY (userId) REFERENCES Users(id),
                           FOREIGN KEY (followerId) REFERENCES Users(id)
);

CREATE TABLE follows (
                         userId VARCHAR(255),
                         followsId VARCHAR(255),
                         PRIMARY KEY (userId, followsId),
                         FOREIGN KEY (userId) REFERENCES Users(id),
                         FOREIGN KEY (followsId) REFERENCES Users(id)
);

CREATE TABLE post (
                      postId BIGINT GENERATED BY DEFAULT AS IDENTITY,
                      text VARCHAR(280),
                      userId BIGINT,
                      authorId BIGINT,
                      author VARCHAR(255),
                      likes INT DEFAULT 0,
                      reposts INT DEFAULT 0,
                      numberComments INT DEFAULT 0,
                      createdAt TIMESTAMP,
                      updatedAt TIMESTAMP,
                      PRIMARY KEY (id),
                      FOREIGN KEY (userId) REFERENCES register(id),
                      FOREIGN KEY (authorId) REFERENCES register(id)
);

CREATE TABLE comments (
                          commentId BIGINT GENERATED BY DEFAULT AS IDENTITY,
                          postId BIGINT,
                          userId BIGINT,
                          text VARCHAR(280),
                          likes INT DEFAULT 0,
                          reposts INT DEFAULT 0,
                          numberComments INT DEFAULT 0,
                          createdAt TIMESTAMP,
                          updatedAt TIMESTAMP,
                          PRIMARY KEY (commentId),
                          FOREIGN KEY (postId) REFERENCES post(postId),
                          FOREIGN KEY (userId) REFERENCES register(id)
);