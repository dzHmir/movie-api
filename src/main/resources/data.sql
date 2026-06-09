PRAGMA foreign_keys = ON;

DELETE FROM movie_actors;
DELETE FROM movie_genres;
DELETE FROM movies;
DELETE FROM actors;
DELETE FROM genres;

INSERT INTO genres (name) VALUES
('Action'),
('Drama'),
('Comedy'),
('Sci-Fi'),
('Horror'),
('Romance'),
('Thriller'),
('Crime'),
('Adventure'),
('Fantasy');

INSERT INTO actors (name, birth_date) VALUES
('Leonardo DiCaprio', '1974-11-11'),
('Morgan Freeman', '1937-06-01'),
('Scarlett Johansson', '1984-11-22'),
('Denzel Washington', '1954-12-28'),
('Natalie Portman', '1981-06-09'),
('Keanu Reeves', '1964-09-02'),
('Charlize Theron', '1975-08-07'),
('Robert Downey Jr.', '1965-04-04'),
('Emma Stone', '1988-11-06'),
('Tom Hanks', '1956-07-09'),
('Jennifer Lawrence', '1990-08-15'),
('Will Smith', '1968-09-25'),
('Gal Gadot', '1985-04-30'),
('Chris Evans', '1981-06-13'),
('Anne Hathaway', '1982-11-12'),
('Johnny Depp', '1963-06-09'),
('Reese Witherspoon', '1976-03-22'),
('Mark Ruffalo', '1967-11-22'),
('Chris Hemsworth', '1983-08-11'),
('Margot Robbie', '1990-07-02');

INSERT INTO movies (title, release_year, duration) VALUES
('The Shawshank Redemption', 1994, 142),
('The Godfather', 1972, 175),
('The Dark Knight', 2008, 152),
('Pulp Fiction', 1994, 154),
('Forrest Gump', 1994, 142),
('Inception', 2010, 148),
('The Matrix', 1999, 136),
('Goodfellas', 1990, 146),
('Interstellar', 2014, 169),
('The Avengers', 2012, 143),
('Titanic', 1997, 194),
('The Lion King', 1994, 88),
('Fight Club', 1999, 139),
('The Social Network', 2010, 120),
('La La Land', 2016, 128),
('Get Out', 2017, 104),
('Mad Max: Fury Road', 2015, 120),
('Parasite', 2019, 132),
('Joker', 2019, 122),
('Avengers: Endgame', 2019, 181),
('The Departed', 2006, 151),
('Whiplash', 2014, 106),
('The Grand Budapest Hotel', 2014, 99),
('Black Panther', 2018, 134),
('A Star Is Born', 2018, 136);

INSERT INTO movie_genres (movie_id, genre_id) VALUES
-- Action
(3, 1), (7, 1), (10, 1), (17, 1), (20, 1),
-- Drama
(1, 2), (2, 2), (3, 2), (4, 2), (5, 2), (6, 2), (8, 2), (9, 2), (11, 2), (13, 2),
-- Comedy
(4, 3), (15, 3), (18, 3), (23, 3),
-- Sci-Fi
(6, 4), (7, 4), (9, 4), (10, 4),
-- Horror
(16, 5), (19, 5),
-- Romance
(5, 6), (11, 6), (15, 6),
-- Thriller
(3, 7), (6, 7), (13, 7), (16, 7),
-- Crime
(2, 8), (3, 8), (4, 8),
-- Adventure
(9, 9), (10, 9), (17, 9),
-- Fantasy
(12, 10), (24, 10);

INSERT INTO movie_actors (movie_id, actor_id) VALUES
-- Leonardo DiCaprio
(6, 1), (21, 1),
-- Morgan Freeman
(1, 2), (2, 2),
-- Scarlett Johansson (The Avengers)
(10, 3),
-- Denzel Washington
(8, 4), (1, 4),
-- Natalie Portman
(6, 5),
-- Keanu Reeves
(7, 6),
-- Charlize Theron
(17, 7),
-- Robert Downey Jr. (The Avengers, Endgame)
(10, 8), (20, 8),
-- Emma Stone
(15, 9), (11, 9),
-- Tom Hanks
(5, 10), (22, 10),
-- Jennifer Lawrence
(19, 11), (2, 11),
-- Will Smith
(16, 12),
-- Gal Gadot
(24, 13),
-- Chris Evans (The Avengers, Endgame)
(10, 14), (20, 14),
-- Anne Hathaway
(25, 15),
-- Johnny Depp
(6, 16), (14, 16),
-- Reese Witherspoon
(23, 17),
-- Mark Ruffalo (The Avengers, Endgame)
(10, 18), (20, 18),
-- Chris Hemsworth (The Avengers, Endgame)
(10, 19), (20, 19),
-- Margot Robbie
(19, 20), (18, 20);
