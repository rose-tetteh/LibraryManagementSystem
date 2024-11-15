package com.example.librarymanagementsystem.model;

import com.example.librarymanagementsystem.enums.ResourceType;

public class LibraryResource {

    private final int resourceId;
    private final String title;
    private final ResourceType resourceType;
    private final String author;

    /**
     * The type Builder.
     *
     * @param <T> the type parameter
     */
    public abstract static class Builder<T extends Builder<T>>{
        private int resourceId;
        private String title;
        private ResourceType resourceType;
        private String author;

        public Builder() {}

        public abstract T getThis();

        public T resourceId(int resourceId){
            this.resourceId = resourceId;
            return getThis();
        }

        public T title(String title){
            this.title = title;
            return getThis();
        }

        public T resourceType(ResourceType resourceType){
            this.resourceType = resourceType;
            return getThis();
        }

        public T author(String author){
            this.author = author;
            return getThis();
        }

        public LibraryResource build(){
            return new LibraryResource(this);
        }
    }

    /**
     * Instantiates a new Library resource.
     *
     * @param builder the builder
     */
    protected LibraryResource(Builder<?> builder){
        this.resourceId = builder.resourceId;
        this.title = builder.title;
        this.author = builder.author;
        this.resourceType = builder.resourceType;
    }

    public int getResourceId() {
        return resourceId;
    }

    public String getTitle() {
        return title;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public String getAuthor() {
        return author;
    }

}
