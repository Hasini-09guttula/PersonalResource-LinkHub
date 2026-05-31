package com.hasini.PersonalResourceLinkHub;

public class Resource {
    private int id;
    private String title;
    private String url;
    private String category;

    // Required no-argument constructor for JSON conversion
    public Resource() {}

    // Constructor to initialize variables easily
    public Resource(int id, String title, String url, String category) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.category = category;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}