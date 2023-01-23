DROP DATABASE IF EXISTS Objects;

CREATE DATABASE Objects;

USE Objects;
CREATE TABLE Users(nickname varchar(100), password varchar(100), nameC varchar(100),id int PRIMARY KEY AUTO_INCREMENT);
INSERT INTO Users(nickname, password, nameC) VALUES
    ('usuario de pruebas', '1234', 'Usuario de Pruebas'),
    ('riki', '1234', 'Ricardo Villanueva Alhama');