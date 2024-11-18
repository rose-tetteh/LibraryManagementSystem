package com.example.librarymanagementsystem.model;

public class Librarian {

    private final int userId;
    private final String username;
    private final String email;
    private final String phoneNumber;
    private final String password;

   public static class LibrarianBuilder{
       private int userId;
       private String username;
       private String email;
       private String phoneNumber;
       private String password;

       public LibrarianBuilder userId(int userId){
           this.userId = userId;
           return this;
       }

       public LibrarianBuilder username(String username){
           this.username = username;
           return this;
       }

       public LibrarianBuilder email(String email){
           this.email = email;
           return this;
       }

       public LibrarianBuilder phoneNumber(String phoneNumber){
           this.phoneNumber = phoneNumber;
           return this;
       }

       public LibrarianBuilder password(String password){
           this.password = password;
           return this;
       }

       public Librarian build(){
           return new Librarian(this);
       }
   }

    public Librarian(LibrarianBuilder builder) {
        this.userId = builder.userId;
        this.username = builder.username;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
        this.password = builder.password;
    }

    public int getUserId() {
        return userId;
    }

   public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

   public String getPassword() {
       return password;
   }

   public String getPhoneNumber() {
       return phoneNumber;
   }

}
