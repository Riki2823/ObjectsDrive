DROP DATABASE IF EXISTS Objects;

CREATE DATABASE Objects;

USE Objects;
CREATE TABLE Users(nickname varchar(100), password varchar(100), nameC varchar(100),PRIMARY KEY(nickname));
INSERT INTO Users(nickname, password, nameC) VALUES
    ('usuario de pruebas', '1234', 'Usuario de Pruebas'),
    ('pere', '1234', 'Pere Negre');

CREATE TABLE Bucket(owner varchar(100), name varchar(100), FOREIGN KEY (owner) REFERENCES Users(nickname), PRIMARY KEY(name));
INSERT INTO Bucket(     owner, name) VALUES
    ('pere', 'notas');

CREATE TABLE Object(id int AUTO_INCREMENT, name varchar(100), owner varchar(100), bucketSrcName varchar(100), date varchar(10), PRIMARY KEY(id), FOREIGN KEY(owner) REFERENCES  Users(nickname), FOREIGN KEY(bucketSrcName) REFERENCES Bucket(name));

CREATE TABLE File(id int AUTO_INCREMENT, content LONGBLOB, hash varchar(100), PRIMARY KEY(id));

CREATE TABLE FileVersion(idrow int AUTO_INCREMENT, idFile int, idObj int, content LONGBLOB,  PRIMARY KEY(idrow) ,FOREIGN KEY(idFile) REFERENCES File(id), FOREIGN KEY(idObj) REFERENCES Object(id));