package com.george.conduit.dto.comment;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.george.conduit.dto.article.AuthorResponse;
import com.george.conduit.model.Comment;

import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeName("comment")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
public class SingleCommentResponse {

    private Long id;
    private String body;
    private Date createdAt;
    private Date updatedAt;
    private AuthorResponse author;

    public SingleCommentResponse(Comment comment) {
        this.id = comment.getId();
        this.body = comment.getBody();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        this.author = new AuthorResponse(comment.getAuthor());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public AuthorResponse getAuthor() {
        return author;
    }

    public void setAuthor(AuthorResponse author) {
        this.author = author;
    }
}
