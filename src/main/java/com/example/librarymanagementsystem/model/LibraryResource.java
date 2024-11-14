package com.example.librarymanagementsystem.model;

public class LibraryResource {

    private int resourceId;
    private String title;
    private ResourceType resourceType;
    private String author;


    /**
     * Instantiates a new Library resource.
     *
     * @param title  the title
     * @param author the author
     */
    public LibraryResource(String title, String author){
        this.title = title;
        this.author = author;
    }

    /**
     * Instantiates a new Library resource.
     *
     * @param resourceId the resource id
     * @param title      the title
     * @param author     the author
     */
    public LibraryResource(int resourceId, String title, String author){
        this.resourceId = resourceId;
        this.title = title;
        this.author = author;
    }

    public int getResourceId() {
        return resourceId;
    }

    public String getTitle() {
        return title;
    }

    public ResourceType getType() {
        return resourceType;
    }

    public void setType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
