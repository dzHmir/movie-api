# Movie API

> A training project built to practice Spring Boot REST API development with JPA, SQLite, and OpenAPI documentation.

RESTful API for managing a movie database with movies, actors, and genres.

## Data Model

- **Movie** — title, release year, duration; linked to many genres and actors
- **Actor** — name, birth date
- **Genre** — name (unique)

## Endpoints

### Movies `/api/movies`
| Method | Path | Description |
|--------|------|-------------|
| POST | `/` | Create a movie |
| GET | `/` | Get all movies (paginated) |
| GET | `/{id}` | Get movie by ID |
| PATCH | `/{id}` | Update movie |
| DELETE | `/{id}` | Delete movie |
| GET | `/search?title=` | Search by title (paginated) |
| GET | `/by-genre?genreId=` | Filter by genre (paginated) |
| GET | `/by-actor?actorId=` | Filter by actor (paginated) |
| GET | `/by-year?year=` | Filter by release year |
| GET | `/{id}/actors` | Get actor IDs of a movie |

### Actors `/api/actors`
| Method | Path | Description |
|--------|------|-------------|
| POST | `/` | Create an actor |
| GET | `/` | Get all actors |
| GET | `/{id}` | Get actor by ID |
| PATCH | `/{id}` | Update actor |
| DELETE | `/{id}` | Delete actor |

### Genres `/api/genres`
| Method | Path | Description |
|--------|------|-------------|
| POST | `/` | Create a genre |
| GET | `/` | Get all genres |
| GET | `/{id}` | Get genre by ID |
| PATCH | `/{id}` | Update genre |
| DELETE | `/{id}` | Delete genre |

## API Documentation

Swagger UI is available at `http://localhost:8081/swagger-ui/index.html` after starting the application.
