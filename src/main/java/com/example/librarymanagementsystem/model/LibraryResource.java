package com.example.librarymanagementsystem.model;

import com.example.librarymanagementsystem.enums.ResourceType;

/**
 * The type Library resource.
 */
public class LibraryResource {
    private final int resourceId;
    private final String title;
    private final ResourceType resourceType;
    private final int numberOfCopies;


    /**
     * The type Builder.
     *
     * @param <T> the type parameter
     */
    public abstract static class Builder<T extends Builder<T>>{
        private int resourceId;
        private String title;
        private ResourceType resourceType;
        private int numberOfCopies;


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

        public T numberOfCopies(int numberOfCopies){
            this.numberOfCopies = numberOfCopies;
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
        this.resourceType = builder.resourceType;
        this.numberOfCopies = builder.numberOfCopies;
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

    public int getNumberOfCopies() {
        return numberOfCopies;
    }
}
