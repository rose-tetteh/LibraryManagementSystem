-- ENUM definitions for resource types and status
CREATE TYPE ResourceType AS ENUM ('book', 'journal');
CREATE TYPE Status AS ENUM ('available', 'pending', 'borrowed', 'overdue');

-- Superclass Table for Library Resources
CREATE TABLE LibraryResource (
                                 resource_id VARCHAR(50) PRIMARY KEY,
                                 title VARCHAR(255) NOT NULL,
                                 resourceType ResourceType NOT NULL
);

-- Subclass Table for Books, extending LibraryResource
CREATE TABLE Book (
                      resource_id VARCHAR(50) PRIMARY KEY,
                      genre VARCHAR(50),
                      author VARCHAR(255),
                      publicationDate DATE,
                      statusOfBookAvailability Status NOT NULL,
                      FOREIGN KEY (resource_id) REFERENCES LibraryResource(resource_id)
);

-- Subclass Table for Journals, extending LibraryResource
CREATE TABLE Journal (
                         resource_id VARCHAR(50) PRIMARY KEY,
                         genre VARCHAR(50),
                         author VARCHAR(255),
                         publicationDate DATE,
                         statusOfJournalAvailability Status NOT NULL,
                         volumeNumber INT,
                         FOREIGN KEY (resource_id) REFERENCES LibraryResource(resource_id)
);

-- Table for Patrons
CREATE TABLE Patron (
                        user_id BIGINT PRIMARY KEY,
                        username VARCHAR(255) NOT NULL UNIQUE,
                        email VARCHAR(255) NOT NULL UNIQUE,
                        password VARCHAR(255) NOT NULL
);

-- Table for Reservations
CREATE TABLE Reservation (
                             reservation_id BIGINT PRIMARY KEY,
                             resource_id VARCHAR(50) NOT NULL,
                             user_id BIGINT NOT NULL,
                             reservationStatus Status NOT NULL,
                             reservationDate TIMESTAMP NOT NULL,
                             queuePosition INT NOT NULL,
                             FOREIGN KEY (resource_id) REFERENCES LibraryResource(resource_id),
                             FOREIGN KEY (user_id) REFERENCES Patron(user_id)
);

-- Table for Transactions
CREATE TABLE Transaction (
                             transaction_id BIGINT PRIMARY KEY,
                             resource_id VARCHAR(50) NOT NULL,
                             user_id BIGINT NOT NULL,
                             transactionStatus Status NOT NULL,
                             transactionDate TIMESTAMP NOT NULL,
                             dueDate TIMESTAMP NOT NULL,
                             FOREIGN KEY (resource_id) REFERENCES LibraryResource(resource_id),
                             FOREIGN KEY (user_id) REFERENCES Patron(user_id)
);
