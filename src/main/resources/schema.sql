#################################################
###                                         ####
### Author: Edem                            ####
### Licence: Edem Akato LLC                 ####
### Date: Sep 27, 2023                      ####
### Version: 1.0                            ####
###                                         ####
#################################################




CREATE SCHEMA IF NOT EXISTS securecapita;
SET NAMES 'UTF8MB4';
SET TIME_ZONE = 'WEST AFRICA';

USE securecapita;
DROP TABLE IF EXISTS Users;
CREATE TABLE Users(
    id           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    firstName    VARCHAR(50) NOT NULL,
    lastName     VARCHAR(50) NOT NULL,
    email        VARCHAR(100) NOT NULL ,
    password     VARCHAR(255) DEFAULT NULL,
    address      VARCHAR(255) DEFAULT NULL,
    phone        VARCHAR(255) DEFAULT NULL,
    title        VARCHAR(50) DEFAULT NULL,
    bio          VARCHAR(255) DEFAULT NULL,
    image_url    VARCHAR(255) DEFAULT 'https://cdn-icons-png.flatation.com/512/149071.png',
    enabled      BOOLEAN DEFAULT FALSE,
    non_locked   BOOLEAN DEFAULT TRUE,
    using_mfa    BOOLEAN DEFAULT FALSE,
    created_at   DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT UQ_Users_Email UNIQUE (email)
);

DROP TABLE IF EXISTS Roles;
CREATE TABLE Roles(
    id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(50) NOT NULL ,
    permission VARCHAR(255) NOT NULL,
    CONSTRAINT UQ_Roles_Name UNIQUE (name )
);


DROP TABLE IF EXISTS UserRole;
CREATE TABLE UserRoles(
     id      BIGINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
     user_id BIGINT UNSIGNED NOT NULL,
     role_id BIGINT UNSIGNED NOT NULL,
     FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE,
     FOREIGN KEY (role_id) REFERENCES Roles (id) ON DELETE RESTRICT ON UPDATE CASCADE,
     CONSTRAINT UQ_UserRoles_User_id UNIQUE (user_id) ##ensure one user has one role
 );


DROP TABLE IF EXISTS Events;
CREATE TABLE Events(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255) NOT NULL CHECK ( type IN ('LOGIN_ATTEMPT','LOGIN_ATTEMPT_FAILURE','LOGIN_ATTEMPT_SUCCESS','PROFILE_UPDATE','PROFILE_PICTURE_UPDATE', 'ROLE_UPDATE','ACCOUNT_SETTINGS_UPDATE', 'PASSWORD_UPDATE','MFA_UPDATE')),
    description VARCHAR(255) NOT NULL,
    CONSTRAINT UQ_Events_type UNIQUE (type)
);


DROP TABLE IF EXISTS UserEvents;
CREATE TABLE UserEvents(
    id BIGINT  UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT UNSIGNED NOT NULL ,
    event_id   BIGINT UNSIGNED NOT NULL,
    device     VARCHAR(255) DEFAULT NULL ,
    ip_address VARCHAR(100) DEFAULT NULL ,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE ,
    FOREIGN KEY (event_id) REFERENCES Events(id) ON DELETE RESTRICT ON UPDATE CASCADE

);


DROP TABLE IF EXISTS AccountVerifications;
CREATE TABLE AccountVerifications (
    id       BIGINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
    user_id  BIGINT UNSIGNED NOT NULL ,
    url      VARCHAR(255) DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT UQ_AccountVerifications_Url UNIQUE (url),
    CONSTRAINT UQ_AccountVerifications_User_id  UNIQUE (user_id)
);









