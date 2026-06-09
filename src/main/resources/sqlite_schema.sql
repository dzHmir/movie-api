CREATE TABLE IF NOT EXISTS movies (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    release_year INTEGER,
    duration INTEGER
);

CREATE TABLE IF NOT EXISTS genres (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS actors (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    birth_date TEXT
);

CREATE TABLE IF NOT EXISTS movie_genres (
    movie_id INTEGER,
    genre_id INTEGER,
    PRIMARY KEY (movie_id, genre_id),
    FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS movie_actors (
    movie_id INTEGER,
    actor_id INTEGER,
    PRIMARY KEY (movie_id, actor_id),
    FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE,
    FOREIGN KEY (actor_id) REFERENCES actors(id) ON DELETE CASCADE
);


CREATE INDEX IF NOT EXISTS idx_movies_title ON movies(title);
CREATE INDEX IF NOT EXISTS idx_movies_release_year ON movies(release_year);
CREATE INDEX IF NOT EXISTS idx_actors_name ON actors(name);
CREATE INDEX IF NOT EXISTS idx_movie_genres_movie_id ON movie_genres(movie_id);
CREATE INDEX IF NOT EXISTS idx_movie_genres_genre_id ON movie_genres(genre_id);
CREATE INDEX IF NOT EXISTS idx_movie_actors_movie_id ON movie_actors(movie_id);
CREATE INDEX IF NOT EXISTS idx_movie_actors_actor_id ON movie_actors(actor_id);