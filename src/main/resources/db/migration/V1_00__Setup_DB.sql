CREATE TABLE table_price
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    title_table   VARCHAR(255),
    content_table LONGTEXT,
    is_deleted BIT (1) DEFAULT 0
);

CREATE TABLE image_dir
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    is_deleted BIT (1) DEFAULT 0
);

CREATE TABLE role
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20),
    is_deleted BIT (1) DEFAULT 0
);

CREATE TABLE branch
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(100),
    address VARCHAR(100),
    is_deleted BIT (1) DEFAULT 0
);

CREATE TABLE contact
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    phone_number VARCHAR(15),
    description  VARCHAR(100),
    is_deleted BIT (1) DEFAULT 0
);

CREATE TABLE category
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(50),
    url_name VARCHAR(255),
    is_deleted BIT (1) DEFAULT 0
);

CREATE TABLE image
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(50),
    link           TEXT,
    topic_name     VARCHAR(100),
    description    TEXT,
    image_category VARCHAR(250),
    is_delete      BIT (1) DEFAULT 0
);

CREATE TABLE topic
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_id BIGINT,
    name        VARCHAR(100),
    url_name    VARCHAR(255),
    description VARCHAR(500),
    icon        VARCHAR(20),
    is_deleted BIT (1) DEFAULT 0,
    FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE TABLE user
(
    id                     BIGINT AUTO_INCREMENT PRIMARY KEY,
    username               VARCHAR(20),
    password               VARCHAR(100),
    role_id                BIGINT,
    full_name              VARCHAR(50),
    email                  VARCHAR(100),
    phone                  VARCHAR(15),
    is_deleted             BIT(1) DEFAULT 0,
    is_activated           BIT(1) DEFAULT 1,
    remember_token         VARCHAR(200),
    last_login             DATETIME,
    verify_code            VARCHAR(20),
    verify_code_expiration VARCHAR(50),
    FOREIGN KEY (role_id) REFERENCES role (id)
);

CREATE TABLE post
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    topic_id    BIGINT,
    name        VARCHAR(200),
    description VARCHAR(500),
    title_image TEXT,
    content     LONGTEXT,
    date_post   DATETIME,
    url_name    VARCHAR(500),
    author      VARCHAR(50),
    is_hidden   TINYINT(1),
    category_id BIGINT,
    icon        VARCHAR(20),
    keyword     VARCHAR(255),
    table_id    LONGTEXT,
    is_deleted BIT (1) DEFAULT 0,
    FOREIGN KEY (topic_id) REFERENCES topic (id),
    FOREIGN KEY (category_id) REFERENCES category (id)
);