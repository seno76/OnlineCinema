-- V1__Baseline.sql
-- Таблицы создаются только если их нет

-- Пользователи
CREATE TABLE IF NOT EXISTS users (
    userId BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    email_verified BOOLEAN DEFAULT FALSE,
    role VARCHAR(20) DEFAULT 'USER',
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Фильмы
CREATE TABLE IF NOT EXISTS movie (
    movieId BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    genre VARCHAR(100),
    year INT,
    rating DOUBLE,
    posterUrl VARCHAR(255),
    movieUrl VARCHAR(255),
    duration INT,
    isCartoon BOOLEAN DEFAULT FALSE,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Сериалы
CREATE TABLE IF NOT EXISTS series (
    seriesId BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    genre VARCHAR(100),
    year INT,
    rating DOUBLE,
    posterUrl VARCHAR(255),
    movieUrl VARCHAR(255),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Сезоны
CREATE TABLE IF NOT EXISTS season (
    seasonId BIGINT PRIMARY KEY AUTO_INCREMENT,
    seriesId BIGINT NOT NULL,
    number INT NOT NULL,
    title VARCHAR(255),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (seriesId) REFERENCES series(seriesId)
);

-- Эпизоды
CREATE TABLE IF NOT EXISTS episode (
    episodeId BIGINT PRIMARY KEY AUTO_INCREMENT,
    seasonId BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    duration INT,
    videoUrl VARCHAR(255),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (seasonId) REFERENCES season(seasonId)
);

-- Настройки пользователей
CREATE TABLE IF NOT EXISTS userPreferences (
    userPreferencesId BIGINT PRIMARY KEY AUTO_INCREMENT,
    userId BIGINT NOT NULL UNIQUE,
    addedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES users(userId)
);

-- Избранные фильмы
CREATE TABLE IF NOT EXISTS userPreferencesMovie (
    UserPreferencesMovieId BIGINT PRIMARY KEY AUTO_INCREMENT,
    userPreferencesId BIGINT NOT NULL,
    movieId BIGINT NOT NULL,
    addedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userPreferencesId) REFERENCES userPreferences(userPreferencesId),
    FOREIGN KEY (movieId) REFERENCES movie(movieId)
);

-- Избранные сериалы
CREATE TABLE IF NOT EXISTS userPreferencesSeries (
    userPreferencesSeriesId BIGINT PRIMARY KEY AUTO_INCREMENT,
    userPreferencesId BIGINT NOT NULL,
    seriesId BIGINT NOT NULL,
    addedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userPreferencesId) REFERENCES userPreferences(userPreferencesId),
    FOREIGN KEY (seriesId) REFERENCES series(seriesId)
);