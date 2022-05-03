package com.george.conduit.dto.article;

import com.george.conduit.model.Article;

public class UpdateArticleRequest {

    private String title;
    private String description;
    private String body;

    public UpdateArticleRequest(String title, String description, String body) {
        this.title = title;
        this.description = description;
        this.body = body;
    }

    public Article toArticleEntity() {
        return new Article(getTitle(), getDescription(), getBody(), null);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
