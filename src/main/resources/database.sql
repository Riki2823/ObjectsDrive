DROP DATABASE IF EXISTS Objects;

CREATE DATABASE Objects;

USE Objects;
CREATE TABLE Users(nickname varchar(100), password varchar(100), nameC varchar(100),PRIMARY KEY(nickname));
INSERT INTO Users(nickname, password, nameC) VALUES
    ('usuario de pruebas', '1234', 'Usuario de Pruebas'),
    ('pere', '1234', 'Pere Negre');

CREATE TABLE Bucket(owner varchar(100), name varchar(100), FOREIGN KEY (owner) REFERENCES Users(nickname));
INSERT INTO Bucket(owner, name) VALUES
    ('pere', 'notas');
