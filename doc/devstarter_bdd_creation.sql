/*==============================================================*/
/* Nom de SGBD :  MySQL 5.0                                     */
/* Date de création :  01/06/2013 15:34:49                      */
/*==============================================================*/


DROP TABLE IF EXISTS COMMENT_USER_PROJECT;

DROP TABLE IF EXISTS DONATION_USER_PROJECT;

DROP TABLE IF EXISTS ENUMERATION;

DROP TABLE IF EXISTS FOLLOW_USER_PROJECT;

DROP TABLE IF EXISTS FORUM;

DROP TABLE IF EXISTS MANAGE_USER_PROJECT;

DROP TABLE IF EXISTS NOTIFICATION;

DROP TABLE IF EXISTS PROJECT;

DROP TABLE IF EXISTS TECHNOLOGY_PROJECT_ENUMERATION;

DROP TABLE IF EXISTS UPLOADED_FILE;

DROP TABLE IF EXISTS USER;

/*==============================================================*/
/* Table : COMMENT_USER_PROJECT                                 */
/*==============================================================*/
CREATE TABLE COMMENT_USER_PROJECT
(
   ID_COMMENT           INT NOT NULL AUTO_INCREMENT,
   USER_ID              INT NOT NULL,
   PROJECT_ID           INT NOT NULL,
   TITLE                VARCHAR(255) NOT NULL,
   MESSAGE              VARCHAR(255),
   CREATED_DATE         DATETIME,
   PRIMARY KEY (ID_COMMENT)
);

/*==============================================================*/
/* Table : DONATION_USER_PROJECT                                */
/*==============================================================*/
CREATE TABLE DONATION_USER_PROJECT
(
   ID_DONATION          INT NOT NULL AUTO_INCREMENT,
   USER_ID              INT NOT NULL,
   PROJECT_ID           INT,
   AMOUNT               INT,
   DATE                 DATETIME,
   TRANSACTION_DETAIL   VARCHAR(255),
   TYPE                 VARCHAR(255),
   PRIMARY KEY (ID_DONATION)
);

/*==============================================================*/
/* Table : ENUMERATION                                          */
/*==============================================================*/
CREATE TABLE ENUMERATION
(
   ID_ENUMERATION       INT NOT NULL AUTO_INCREMENT,
   NAME                 VARCHAR(255),
   VALUE                VARCHAR(255),
   RANK                 INT,
   ACTIVE               INT,
   PARENT_ID            INT,
   TYPE_ID              INT,
   PRIMARY KEY (ID_ENUMERATION)
);

/*==============================================================*/
/* Table : FOLLOW_USER_PROJECT                                  */
/*==============================================================*/
CREATE TABLE FOLLOW_USER_PROJECT
(
   ID_FOLLOW            INT NOT NULL AUTO_INCREMENT,
   PROJECT_ID           INT NOT NULL,
   USER_ID              INT NOT NULL,
   PRIMARY KEY (ID_FOLLOW)
);

/*==============================================================*/
/* Table : FORUM                                                */
/*==============================================================*/
CREATE TABLE FORUM
(
   ID_FORUM             INT NOT NULL AUTO_INCREMENT,
   TYPE_ENUM_ID         INT NOT NULL,
   USER_ID              INT NOT NULL,
   TITLE                VARCHAR(255),
   MESSAGE              VARCHAR(255),
   DATE_CREATED         DATETIME,
   PARENT_ID            INT,
   PRIMARY KEY (ID_FORUM)
);

/*==============================================================*/
/* Table : MANAGE_USER_PROJECT                                  */
/*==============================================================*/
CREATE TABLE MANAGE_USER_PROJECT
(
   ID_MANAGE            INT NOT NULL AUTO_INCREMENT,
   PROJECT_ID           INT NOT NULL,
   USER_ID              INT NOT NULL,
   PRIMARY KEY (ID_MANAGE)
);

/*==============================================================*/
/* Table : NOTIFICATION                                         */
/*==============================================================*/
CREATE TABLE NOTIFICATION
(
   ID_NOTIFICATION      INT NOT NULL AUTO_INCREMENT,
   USER_ID              INT NOT NULL,
   TITLE                LONGTEXT,
   MESSAGE              LONGTEXT,
   DATE_CREATED         DATETIME,
   PRIMARY KEY (ID_NOTIFICATION)
);

/*==============================================================*/
/* Table : PROJECT                                              */
/*==============================================================*/
CREATE TABLE PROJECT
(
   ID_PROJECT           INT NOT NULL AUTO_INCREMENT,
   TYPE_ID              INT,
   NAME                 VARCHAR(255),
   DESCRIPTION          TEXT,
   PICTURE_URL          VARCHAR(255),
   PICTURE_BYTES        LONGBLOB,
   START_DATE           DATE,
   MIN_END_DATE         DATE,
   EFFECTIVE_END_DATE   DATE,
   MAX_END_DATE         DATE,
   FUND                 INT,
   RANK                 INT,
   ACTIVE               INT,
   DATE_CREATED         DATETIME,
   DATE_UPDATED         DATETIME,
   PRIMARY KEY (ID_PROJECT)
);

/*==============================================================*/
/* Table : TECHNOLOGY_PROJECT_ENUMERATION                       */
/*==============================================================*/
CREATE TABLE TECHNOLOGY_PROJECT_ENUMERATION
(
   ID_TECHNOLOGY        INT NOT NULL AUTO_INCREMENT,
   TECHNO_ENUM_ID       INT NOT NULL,
   PROJECT_ID           INT NOT NULL,
   PRIMARY KEY (ID_TECHNOLOGY)
);

/*==============================================================*/
/* Table : UPLOADED_FILE                                        */
/*==============================================================*/
CREATE TABLE UPLOADED_FILE
(
   ID_FILE              INT NOT NULL AUTO_INCREMENT,
   PROJECT_ID           INT NOT NULL,
   URL                  VARCHAR(255),
   TITLE                VARCHAR(255),
   BYTES                LONGBLOB,
   DATE_CREATED         DATETIME,
   DATE_UPDATED         DATETIME,
   PRIMARY KEY (ID_FILE)
);

/*==============================================================*/
/* Table : USER                                                 */
/*==============================================================*/
CREATE TABLE USER
(
   ID_USER              INT NOT NULL AUTO_INCREMENT,
   JOB_ENUM_ID          INT,
   COMPTE_ENUM_ID       INT NOT NULL,
   NAME                 VARCHAR(255),
   FIRSTNAME            VARCHAR(255),
   EMAIL                VARCHAR(255),
   PASSWORD             VARCHAR(100),
   PROFIL               TEXT,
   WALLET               INT,
   ACTIVE               INT,
   CREATED_DATE         DATETIME,
   NOTIFICATION         INT,
   PRIMARY KEY (ID_USER)
);

ALTER TABLE COMMENT_USER_PROJECT ADD CONSTRAINT FK_COMMENT_USER_PROJECT FOREIGN KEY (USER_ID)
      REFERENCES USER (ID_USER) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE COMMENT_USER_PROJECT ADD CONSTRAINT FK_COMMENT_USER_PROJECT2 FOREIGN KEY (PROJECT_ID)
      REFERENCES PROJECT (ID_PROJECT) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DONATION_USER_PROJECT ADD CONSTRAINT FK_DONATION_USER_PROJECT FOREIGN KEY (USER_ID)
      REFERENCES USER (ID_USER) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE DONATION_USER_PROJECT ADD CONSTRAINT FK_DONATION_USER_PROJECT2 FOREIGN KEY (PROJECT_ID)
      REFERENCES PROJECT (ID_PROJECT) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE FOLLOW_USER_PROJECT ADD CONSTRAINT FK_FOLLOW_USER_PROJECT FOREIGN KEY (PROJECT_ID)
      REFERENCES PROJECT (ID_PROJECT) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE FOLLOW_USER_PROJECT ADD CONSTRAINT FK_FOLLOW_USER_PROJECT2 FOREIGN KEY (USER_ID)
      REFERENCES USER (ID_USER) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE FORUM ADD CONSTRAINT FK_ECHANGE FOREIGN KEY (USER_ID)
      REFERENCES USER (ID_USER) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE FORUM ADD CONSTRAINT FK_SE_DEFINIT2 FOREIGN KEY (TYPE_ENUM_ID)
      REFERENCES ENUMERATION (ID_ENUMERATION) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE MANAGE_USER_PROJECT ADD CONSTRAINT FK_MANAGE_USER_PROJECT FOREIGN KEY (PROJECT_ID)
      REFERENCES PROJECT (ID_PROJECT) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE MANAGE_USER_PROJECT ADD CONSTRAINT FK_MANAGE_USER_PROJECT2 FOREIGN KEY (USER_ID)
      REFERENCES USER (ID_USER) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE NOTIFICATION ADD CONSTRAINT FK_RECOIT FOREIGN KEY (USER_ID)
      REFERENCES USER (ID_USER) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PROJECT ADD CONSTRAINT FK_TYPE_PROJET FOREIGN KEY (TYPE_ID)
      REFERENCES ENUMERATION (ID_ENUMERATION) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TECHNOLOGY_PROJECT_ENUMERATION ADD CONSTRAINT FK_TECHNOLOGY_PROJECT_ENUMERATION FOREIGN KEY (TECHNO_ENUM_ID)
      REFERENCES ENUMERATION (ID_ENUMERATION) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TECHNOLOGY_PROJECT_ENUMERATION ADD CONSTRAINT FK_TECHNOLOGY_PROJECT_ENUMERATION2 FOREIGN KEY (PROJECT_ID)
      REFERENCES PROJECT (ID_PROJECT) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE UPLOADED_FILE ADD CONSTRAINT FK_JOIN_PROJECT_FILE FOREIGN KEY (PROJECT_ID)
      REFERENCES PROJECT (ID_PROJECT) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE USER ADD CONSTRAINT FK_EXERCE FOREIGN KEY (JOB_ENUM_ID)
      REFERENCES ENUMERATION (ID_ENUMERATION) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE USER ADD CONSTRAINT FK_S_AUTHENTIFIE FOREIGN KEY (COMPTE_ENUM_ID)
      REFERENCES ENUMERATION (ID_ENUMERATION) ON DELETE RESTRICT ON UPDATE RESTRICT;

