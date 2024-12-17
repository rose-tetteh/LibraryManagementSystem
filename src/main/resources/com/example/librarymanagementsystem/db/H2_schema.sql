CREATE TABLE IF NOT EXISTS Patron (
    patronId INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    patronLibraryId VARCHAR(255) UNIQUE NOT NULL,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phoneNumber VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL)