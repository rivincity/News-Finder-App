package com.example.newsproject;

public class News {
    String title;
    String description;
    String link;
    String datePublished;

    public News(String title, String description, String link, String datePublished) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.datePublished = datePublished;
    }
    public String getTitle()
    {
        return title;
    }
    public String getDescription()
    {
        return description;
    }
    public String getLink()
    {
        return link;
    }
    public String getDatePublished() {
        return datePublished;
    }
}
