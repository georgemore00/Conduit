package com.george.conduit.dto.article;

import com.george.conduit.model.Article;
import com.george.conduit.model.Tag;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CreateArticleRequest {

    @NotBlank(message = "title cannot be blank.")
    private String title;

    @NotBlank(message = "description cannot be blank.")
    private String description;

    @NotBlank(message = "body cannot be blank.")
    private String body;

    private List<String> tags;

    public CreateArticleRequest(String title, String description, String body, List<String> tags) {
        this.title = title;
        this.description = description;
        this.body = body;
        this.tags = tags;
    }

    public Article toArticleEntity() {
        List<Tag> tagList = new ArrayList<>();
        if (getTags() != null) {
            tagList = getTags().stream().map(name -> new Tag(name)).collect(Collectors.toList());
        }
        return new Article(getTitle(), getDescription(), getBody(), tagList);
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
