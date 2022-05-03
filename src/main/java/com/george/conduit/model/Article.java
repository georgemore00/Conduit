package com.george.conduit.model;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.text.Normalizer;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

@Entity
public class Article {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String slug;
    private String description;
    private String body;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @Transient
    private Boolean isLiked = false;

    private int likesCount;

    @ManyToMany
    private Set<Profile> likedBy;

    @ManyToOne()
    private Profile author;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private List<Tag> tags;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private Set<Comment> comments;

    public Article() {
    }

    public Article(String title, String description, String body, List<Tag> tags) {
        this.title = title;
        this.slug = toSlug(title);
        this.description = description;
        this.body = body;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
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

    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public Profile getAuthor() {
        return author;
    }

    public void setAuthor(Profile author) {
        this.author = author;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void setLikedBy(Set<Profile> likedBy) {
        this.likedBy = likedBy;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Boolean getLiked() {
        return isLiked;
    }

    public void setLiked(Boolean liked) {
        isLiked = liked;
    }

    public Set<Profile> getLikedBy() {
        return likedBy;
    }

    public Article updateArticle(Article newArticle) {
        if (!StringUtils.isEmpty(newArticle.getTitle())) {
            setTitle(newArticle.getTitle());
            setSlug(toSlug(newArticle.getSlug()));
        }
        if (!StringUtils.isEmpty(newArticle.getDescription())) {
            setDescription(newArticle.getDescription());
        }
        if (!StringUtils.isEmpty(newArticle.getBody())) {
            setBody(newArticle.getBody());
        }
        return this;
    }

    public Article viewArticle(Profile viewer) {
        if (this.likedBy.contains(viewer)) {
            setLiked(true);
        }
        return this;
    }

    public Article addLike(Profile from) {
        if (!this.likedBy.contains(from)) {
            this.likedBy.add(from);
            this.likesCount += 1;
        }
        setLiked(true);
        return this;
    }

    public Article removeLike(Profile from) {
        if (likedBy.contains(from)) {
            this.likedBy.remove(from);
            this.likesCount -= 1;
        }
        setLiked(false);
        return this;
    }

    private static String toSlug(String input) {
        Pattern NONLATIN = Pattern.compile("[^\\w-]");
        Pattern WHITESPACE = Pattern.compile("[\\s]");
        if (input != null) {
            String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
            String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
            String slug = NONLATIN.matcher(normalized).replaceAll("");
            return slug.toLowerCase(Locale.ENGLISH);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", slug='" + slug + '\'' +
                ", description='" + description + '\'' +
                ", body='" + body + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", liked=" + isLiked +
                ", likesCount=" + likesCount +
                ", likedBy=" + likedBy +
                ", author=" + author +
                ", tags=" + tags +
                ", comments=" + comments +
                '}';
    }
}
