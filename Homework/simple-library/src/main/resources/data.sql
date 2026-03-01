INSERT INTO author (author_name) VALUES
('J.K. Rowling'),
('George Orwell'),
('James Clear'),
('Robert Martin'),
('Joshua Bloch');

INSERT INTO book (book_name, edition, release_date) VALUES
('Harry Potter', 1, '1997-06-26'),
('1984', 1, '1949-06-08'),
('Atomic Habits', 1, '2018-10-16'),
('Clean Code', 1, '2008-08-01'),
('Effective Java', 3, '2018-01-06');

INSERT INTO book_author (book_id, author_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);