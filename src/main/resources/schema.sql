DROP TABLE IF EXISTS recipes;

CREATE TABLE recipes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    description VARCHAR(1000),
    cuisine VARCHAR(100),
    difficulty VARCHAR(50),
    time_label VARCHAR(50),
    image_url VARCHAR(2048),
    is_favorite BOOLEAN NOT NULL DEFAULT FALSE,
    servings_count INT,
    ingredients VARCHAR(4000),
    steps VARCHAR(4000),
    images VARCHAR(4000),
    tags VARCHAR(1000),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
