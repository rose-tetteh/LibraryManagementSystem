-- ENUM definitions for resource types and status
CREATE TYPE ResourceType AS ENUM ('book', 'journal');
CREATE TYPE Status AS ENUM ('available', 'pending', 'borrowed', 'overdue');

-- Superclass Table for Library Resources
CREATE TABLE LibraryResource (
                                 resourceId INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                 title VARCHAR(255) NOT NULL,
                                 resourceType ResourceType NOT NULL,
                                 numberOfCopies INT
);

-- Subclass Table for Books, extending LibraryResource
CREATE TABLE Book (
                      resourceId INT PRIMARY KEY,
                      genre VARCHAR(50),
                      author VARCHAR(255),
                      isbn VARCHAR(255) UNIQUE,
                      publicationDate DATE,
                      statusOfBookAvailability Status NOT NULL,
                      FOREIGN KEY (resourceId) REFERENCES LibraryResource(resourceId)
);

-- Subclass Table for Journals, extending LibraryResource
CREATE TABLE Journal (
                         resourceId INT PRIMARY KEY,
                         genre VARCHAR(50),
                         author VARCHAR(255),
                         volumeNumber INT,
                         publicationDate DATE,
                         statusOfJournalAvailability Status NOT NULL,
                         FOREIGN KEY (resourceId) REFERENCES LibraryResource(resourceId)
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
                        userId INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                        username VARCHAR(255) NOT NULL UNIQUE,
                        email VARCHAR(255) NOT NULL UNIQUE,
                        phoneNumber VARCHAR(255) NOT NULL,
                        password VARCHAR(255) NOT NULL
);

-- Table for Reservations
CREATE TABLE Reservation (
                             reservationId INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                             resourceId INT NOT NULL,
                             patronLibraryId VARCHAR(255) NOT NULL,
                             reservationStatus Status NOT NULL,
                             reservationDate TIMESTAMP NOT NULL,
                             queuePosition INT NOT NULL,
                             FOREIGN KEY (resourceId) REFERENCES LibraryResource(resourceId),
                             FOREIGN KEY (patronLibraryId) REFERENCES Patron(patronLibraryId)
);

-- Table for Transactions
CREATE TABLE Transaction (
                             transactionId INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                             resourceId INT NOT NULL,
                             patronLibraryId VARCHAR(255) NOT NULL,
                             transactionStatus Status NOT NULL,
                             transactionDate TIMESTAMP NOT NULL,
                             dueDate TIMESTAMP NOT NULL,
                             FOREIGN KEY (resourceId) REFERENCES LibraryResource(resourceId),
                             FOREIGN KEY (patronLibraryId) REFERENCES Patron(patronLibraryId)
);
