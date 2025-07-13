DB Creation Script Run pgAdmin 

CREATE DATABASE wellness_db;


CREATE TABLE users (
    student_number VARCHAR(20) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15) NOT NULL,
    password TEXT NOT NULL
);

CREATE USER wellness_user WITH PASSWORD 'wellness123';

GRANT CONNECT ON DATABASE wellness_db TO wellness_user;
GRANT USAGE ON SCHEMA public TO wellness_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO wellness_user;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO wellness_user;
