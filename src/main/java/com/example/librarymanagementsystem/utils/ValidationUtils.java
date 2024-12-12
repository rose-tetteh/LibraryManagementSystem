package com.example.librarymanagementsystem.utils;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class ValidationUtils {

    // Email validation regex
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    // Phone number validation regex (adjustable based on specific requirements)
    private static final String PHONE_REGEX = "^\\+?\\d{10,14}$";

    // ISBN validation regex
    private static final String ISBN_REGEX = "^(?:ISBN(?:-10)?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$)[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$";

    private static final String LIBRARY_ID_PATTERN = "LIB-\\d{8}-\\d{2}";

    /**
     * Validate Book Information
     */
    public static class BookValidation {
        public static ValidationResult validateBookTitle(String title) {
            if (title == null || title.trim().isEmpty()) {
                return ValidationResult.invalid("Book title cannot be empty");
            }
            if (title.length() < 2 || title.length() > 255) {
                return ValidationResult.invalid("Book title must be between 2 and 255 characters");
            }
            return ValidationResult.valid();
        }

        public static ValidationResult validateISBN(String isbn) {
            if (isbn == null || isbn.trim().isEmpty()) {
                return ValidationResult.invalid("ISBN cannot be empty");
            }
            if (!Pattern.matches(ISBN_REGEX, isbn)) {
                return ValidationResult.invalid("Invalid ISBN format");
            }
            return ValidationResult.valid();
        }

        public static ValidationResult validatePublicationYear(Integer year) {
            if (year == null) {
                return ValidationResult.invalid("Publication year cannot be null");
            }
            int currentYear = LocalDate.now().getYear();
            if (year < 1000 || year > currentYear) {
                return ValidationResult.invalid("Invalid publication year");
            }
            return ValidationResult.valid();
        }
    }

    /**
     * Validate Patron Information
     */
    public static class PatronValidation {
        public static ValidationResult validateName(String name) {
            if (name == null || name.trim().isEmpty()) {
                return ValidationResult.invalid("Name cannot be empty");
            }
            if (name.length() < 2 || name.length() > 100) {
                return ValidationResult.invalid("Name must be between 2 and 100 characters");
            }
            // Optional: Check for valid name characters
            if (!name.matches("^[a-zA-Z\\s'-]+$")) {
                return ValidationResult.invalid("Name contains invalid characters");
            }
            return ValidationResult.valid();
        }

        public static ValidationResult validateEmail(String email) {
            if (email == null || email.trim().isEmpty()) {
                return ValidationResult.invalid("Email cannot be empty");
            }
            if (!Pattern.matches(EMAIL_REGEX, email)) {
                return ValidationResult.invalid("Invalid email format");
            }
            return ValidationResult.valid();
        }

        public static ValidationResult validatePhoneNumber(String phoneNumber) {
            if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
                return ValidationResult.invalid("Phone number cannot be empty");
            }
            if (!Pattern.matches(PHONE_REGEX, phoneNumber)) {
                return ValidationResult.invalid("Invalid phone number format");
            }
            return ValidationResult.valid();
        }

    }

    public static ValidationResult validateLibraryId(String patronLibraryId){
        if (patronLibraryId == null || patronLibraryId.trim().isEmpty()) {
            return ValidationResult.invalid("Library ID cannot be empty");
        }
        if (!Pattern.matches(LIBRARY_ID_PATTERN, patronLibraryId)) {
            return ValidationResult.invalid("Invalid library ID format! Expected format: LIB-12345678-90");
        }
        return ValidationResult.valid();
    }

    /**
     * Validate Transaction Information
     */
    public static class TransactionValidation {
        public static ValidationResult validateLoanPeriod(LocalDate borrowDate, LocalDate dueDate) {
            if (borrowDate == null || dueDate == null) {
                return ValidationResult.invalid("Dates cannot be null");
            }
            if (dueDate.isBefore(borrowDate)) {
                return ValidationResult.invalid("Due date cannot be before borrow date");
            }
            if (borrowDate.isAfter(LocalDate.now())) {
                return ValidationResult.invalid("Borrow date cannot be in the future");
            }
            long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(borrowDate, dueDate);
            if (daysBetween < 1 || daysBetween > 60) {
                return ValidationResult.invalid("Loan period must be between 1 and 60 days");
            }
            return ValidationResult.valid();
        }
    }

    /**
     * Validation Result Class
     */
    public static class ValidationResult {
        private final boolean isValid;
        private final String errorMessage;

        private ValidationResult(boolean isValid, String errorMessage) {
            this.isValid = isValid;
            this.errorMessage = errorMessage;
        }

        public static ValidationResult valid() {
            return new ValidationResult(true, null);
        }

        public static ValidationResult invalid(String errorMessage) {
            return new ValidationResult(false, errorMessage);
        }

        public boolean isValid() {
            return isValid;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        @Override
        public String toString() {
            return isValid ? "Valid" : "Invalid: " + errorMessage;
        }
    }

    /**
     * Comprehensive Validation Method
     */
    public static class ComplexValidation {
        public static ValidationResult validatePatronRegistration(
                String name,
                String email,
                String phoneNumber,
                LocalDate dateOfBirth
        ) {
            // Perform multiple validations
            ValidationResult nameResult = PatronValidation.validateName(name);
            if (!nameResult.isValid()) return nameResult;

            ValidationResult emailResult = PatronValidation.validateEmail(email);
            if (!emailResult.isValid()) return emailResult;

            ValidationResult phoneResult = PatronValidation.validatePhoneNumber(phoneNumber);
            if (!phoneResult.isValid()) return phoneResult;

//            ValidationResult ageResult = PatronValidation.validateAge(dateOfBirth);
//            if (!ageResult.isValid()) return ageResult;

            return ValidationResult.valid();
        }

        public static ValidationResult validateBookEntry(
                String title,
                String isbn,
                Integer publicationYear
        ) {
            ValidationResult titleResult = BookValidation.validateBookTitle(title);
            if (!titleResult.isValid()) return titleResult;

            ValidationResult isbnResult = BookValidation.validateISBN(isbn);
            if (!isbnResult.isValid()) return isbnResult;

            ValidationResult yearResult = BookValidation.validatePublicationYear(publicationYear);
            if (!yearResult.isValid()) return yearResult;

            return ValidationResult.valid();
        }
    }
}