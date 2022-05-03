package com.george.conduit.service;

import com.george.conduit.exception.CommentNotFoundException;
import com.george.conduit.model.Article;
import com.george.conduit.model.Comment;
import com.george.conduit.model.Profile;
import com.george.conduit.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ProfileService profileService;

    public Comment addCommentToArticle(String currentUser, String slug, String body) {
        Article article = articleService.viewArticle(currentUser, slug);
        Profile commenterProfile = profileService.getProfile(null, currentUser);
        Comment newComment = new Comment(body, article, commenterProfile);
        return commentRepository.save(newComment);
    }

    public List<Comment> getComments(String currentUser, String slug) {
        Article article = articleService.viewArticle(currentUser, slug);
        List<Comment> comments = commentRepository.findByArticleId(article.getId());
        comments.stream().forEach(c -> c.setAuthor(profileService.getProfile(currentUser, c.getAuthor().getProfileName())));
        return comments;
    }

    public void deleteComment(String currentUser, String slug, Long id) {
        Article article = articleService.viewArticle(currentUser, slug);
        Profile currentProfile = profileService.getProfile(null, currentUser);
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException("Comment not found"));

        if (comment.getAuthor().getId() == currentProfile.getId()) {
            commentRepository.delete(comment);
        } else {
            throw new BadCredentialsException("Only the author of tbe comment can delete the comment.");
        }
    }
}
