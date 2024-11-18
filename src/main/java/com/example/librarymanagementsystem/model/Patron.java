package com.example.librarymanagementsystem.model;

public class Patron {

    private final int patronId;
    private final String patronLibraryId;
    private final String username;
    private final String email;
    private final String phoneNumber;
    private final String address;

   public static class PatronBuilder{
       private int patronId;
       private String patronLibraryId;
       private String username;
       private String email;
       private String phoneNumber;
       private String address;

       public PatronBuilder patronId(int patronId){
           this.patronId = patronId;
           return this;
       }

       public PatronBuilder patronLibraryId(String patronLibraryId){
           this.patronLibraryId = patronLibraryId;
           return this;
       }

       public PatronBuilder username(String username){
           this.username = username;
           return this;
       }

       public PatronBuilder email(String email){
           this.email = email;
           return this;
       }

       public PatronBuilder phoneNumber(String phoneNumber){
           this.phoneNumber = phoneNumber;
           return this;
       }

       public PatronBuilder address(String address){
           this.address = address;
           return this;
       }

       public Patron build(){
           return new Patron(this);
       }
   }

    public Patron(PatronBuilder builder) {
        this.patronId = builder.patronId;
        this.patronLibraryId = builder.patronLibraryId;
        this.username = builder.username;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
        this.address = builder.address;
    }

    public int getPatronId() {
        return patronId;
    }

    public String getPatronLibraryId() {
       return patronLibraryId;
    }

   public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

   public String getPhoneNumber() {
       return phoneNumber;
   }
   public String getAddress() {
       return address;
   }

}
