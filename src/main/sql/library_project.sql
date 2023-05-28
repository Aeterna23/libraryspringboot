CREATE TABLE person
(
    id         SERIAL PRIMARY KEY,
    username   VARCHAR(100) NOT NULL UNIQUE,
    full_name  VARCHAR      NOT NULL,
    birth_date INT          NOT NULL,
    password   VARCHAR      NOT NULL
);

INSERT INTO person(username, full_name, birth_date, "password") VALUES
('Nik', 'Nik Jonson', 1990, '12345'),
('Thomas', 'Thomas Anderson', 1991, '54321'),
('Elsa', 'Elsa Jean', 1988, 'sdrr'),
('Edward', 'Edward Bertman', 1954, 'cow');
