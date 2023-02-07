    DROP DATABASE IF EXISTS Objects;

    CREATE DATABASE Objects;

    USE Objects;
    CREATE TABLE Users(nickname varchar(100), password varchar(100), nameC varchar(100),PRIMARY KEY(nickname));

    CREATE TABLE Bucket(owner varchar(100), name varchar(100), FOREIGN KEY (owner) REFERENCES Users(nickname) ON DELETE CASCADE,  PRIMARY KEY(name));

    CREATE TABLE Object(id int AUTO_INCREMENT, uri varchar(500), name varchar(100), owner varchar(100), bucketSrcName varchar(100), date varchar(10), PRIMARY KEY(id), FOREIGN KEY(owner) REFERENCES Users(nickname), FOREIGN KEY(bucketSrcName) REFERENCES Bucket(name) ON DELETE CASCADE);

    CREATE TABLE File(id int AUTO_INCREMENT, content LONGBLOB, hash varchar(100), PRIMARY KEY(id));

    CREATE TABLE FileVersion(idrow int AUTO_INCREMENT, idFile int, idObj int, fechaMod TIMESTAMP DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY(idrow) ,FOREIGN KEY(idFile) REFERENCES File(id) ON DELETE CASCADE, FOREIGN KEY(idObj) REFERENCES Object(id) ON DELETE CASCADE);