-- H2 Database Schema for Library Management System

-- Create ENUM-like types using CHECK constraints for H2
CREATE TABLE IF NOT EXISTS ResourceTypeEnum (
                                                type VARCHAR(20) PRIMARY KEY
    );
MERGE INTO ResourceTypeEnum (type) VALUES
                                       ('book'),
                                       ('journal');

CREATE TABLE IF NOT EXISTS StatusEnum (
                                          status VARCHAR(20) PRIMARY KEY
    );
MERGE INTO StatusEnum (status) VALUES
                                    ('available'),
                                    ('pending'),
                                    ('borrowed'),
                                    ('overdue');

-- Superclass Table for Library Resources
CREATE TABLE LibraryResource (
                                 resourceId INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                 title VARCHAR(255) NOT NULL,
                                 resourceType VARCHAR(20) NOT NULL,
                                 numberOfCopies INT,
                                 FOREIGN KEY (resourceType) REFERENCES ResourceTypeEnum(type)
);

-- Subclass Table for Books, extending LibraryResource
CREATE TABLE Book (
                      resourceId INT PRIMARY KEY,
                      genre VARCHAR(50),
                      author VARCHAR(255),
                      isbn VARCHAR(255),
                      publicationDate DATE,
                      statusOfBookAvailability VARCHAR(20) NOT NULL,
                      FOREIGN KEY (resourceId) REFERENCES LibraryResource(resourceId),
                      FOREIGN KEY (statusOfBookAvailability) REFERENCES StatusEnum(status)
);

-- Subclass Table for Journals, extending LibraryResource
CREATE TABLE Journal (
                         resourceId INT PRIMARY KEY,
                         genre VARCHAR(50),
                         author VARCHAR(255),
                         volumeNumber INT,
                         publicationDate DATE,
                         statusOfJournalAvailability VARCHAR(20) NOT NULL,
                         FOREIGN KEY (resourceId) REFERENCES LibraryResource(resourceId),
                         FOREIGN KEY (statusOfJournalAvailability) REFERENCES StatusEnum(status)
);

-- Table for Patrons
CREATE TABLE Patron (
                        patronId INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                        patronLibraryId VARCHAR(255) NOT NULL UNIQUE,
                        username VARCHAR(255) NOT NULL UNIQUE,
                        email VARCHAR(255) NOT NULL UNIQUE,
                        phoneNumber VARCHAR(255) NOT NULL,
                        address VARCHAR(255) NOT NULL
);

-- Table for Librarian
CREATE TABLE Librarian (
                           userId INT PRIMARY KEY,
                           username VARCHAR(255) NOT NULL UNIQUE,
                           email VARCHAR(255) NOT NULL UNIQUE,
                           phoneNumber VARCHAR(255) NOT NULL,
                           password VARCHAR(255) NOT NULL
);

-- Table for Reservations
CREATE TABLE Reservations (
                             reservationId INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                             resourceId INT NOT NULL,
                             patronLibraryId VARCHAR(255) NOT NULL,
                             reservationStatus VARCHAR(20) NOT NULL,
                             reservationDate TIMESTAMP NOT NULL,
                             queuePosition INT NOT NULL,
                             FOREIGN KEY (resourceId) REFERENCES LibraryResource(resourceId),
                             FOREIGN KEY (patronLibraryId) REFERENCES Patron(patronLibraryId),
                             FOREIGN KEY (reservationStatus) REFERENCES StatusEnum(status)
);

-- Table for Transactions
CREATE TABLE Transactions (
                             transactionId INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                             resourceId INT NOT NULL,
                             patronLibraryId VARCHAR(255) NOT NULL,
                             transactionStatus VARCHAR(20) NOT NULL,
                             transactionDate TIMESTAMP NOT NULL,
                             dueDate TIMESTAMP NOT NULL,
                             FOREIGN KEY (resourceId) REFERENCES LibraryResource(resourceId),
                             FOREIGN KEY (patronLibraryId) REFERENCES Patron(patronLibraryId),
                             FOREIGN KEY (transactionStatus) REFERENCES StatusEnum(status)
);