CREATE TABLE IF NOT EXISTS Petition (
    id SERIAL PRIMARY KEY ,
    title varchar(255) NOT NULL,
    description TEXT,
    date DATETIME NOT NULL,
    signatures TEXT
);

INSERT INTO Petition (title, description, date, signatures) 
VALUES 
('Petition 1', 'Some description 1', CURRENT_TIMESTAMP, 'John Joe 1 Mickey Mack 1'),
('Petition 2', 'Some description 2', CURRENT_TIMESTAMP, 'John Joe 2, Mickey Mack 2'),
('Petition 3', 'Some description 3', CURRENT_TIMESTAMP, 'John Joe 3, Mickey Mack 3');
