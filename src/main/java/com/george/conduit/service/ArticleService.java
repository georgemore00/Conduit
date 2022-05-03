package com.george.conduit.service;

import com.george.conduit.exception.ArticleNotFoundException;
import com.george.conduit.exception.ProfileNotFoundException;
import com.george.conduit.exception.TitleAlreadyExistsException;
import com.george.conduit.model.Article;
import com.george.conduit.model.Profile;
import com.george.conduit.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private TagService tagService;

    public Article createArticle(String author, Article newArticle) {
        if (articleRepository.findByTitle(newArticle.getTitle()).isPresent()) {
            throw new TitleAlreadyExistsException("Title already exists");
        }
        
        Profile profile = profileService.getProfile(null, author);
        newArticle.setAuthor(profile);
        newArticle.setTags(tagService.refresh(newArticle.getTags()));
        return articleRepository.save(newArticle);
    }

    public Article updateArticle(String author, String slug, Article newArticle) {
        Article article = articleRepository.findBySlug(slug).orElseThrow(() -> new ArticleNotFoundException("Article not found"));
        if (articleRepository.findByTitle(newArticle.getTitle()).isPresent()) {
            throw new TitleAlreadyExistsException("Title already exists");
        }
        if (!article.getAuthor().getProfileName().equals(author)) {
            throw new BadCredentialsException("Only the author can update the article");
        }
        return articleRepository.save(article.updateArticle(newArticle));
    }

    public void deleteArticle(String currentUser, String slug) {
        Article article = articleRepository.findBySlug(slug).orElseThrow(() -> new ArticleNotFoundException("Article not found"));
        if (!article.getAuthor().getProfileName().equals(currentUser)) {
            throw new BadCredentialsException("Only the author can delete the article");
        }
        articleRepository.delete(article);
    }

    //Checks if the viewer is following the author or has liked the article
    public Article viewArticle(String viewer, String slug) {
        Article article = articleRepository.findBySlug(slug).orElseThrow(() -> new ArticleNotFoundException("Article not found"));
        Profile authorProfile = profileService.getProfile(viewer, article.getAuthor().getProfileName());
        article.setAuthor(authorProfile);

        try {
            Profile viewerProfile = profileService.getProfile(null, viewer);
            article = article.viewArticle(viewerProfile);
        } catch (ProfileNotFoundException e) {
            return article;
        }
        return article;
    }

    public Article addLike(String currentUser, String slug) {
        Article article = articleRepository.findBySlug(slug).orElseThrow(() -> new ArticleNotFoundException("Article not found"));
        Profile currentProfile = profileService.getProfile(null, currentUser);
        return articleRepository.save(article.addLike(currentProfile));
    }

    public Article removeLike(String currentUser, String slug) {
        Article article = articleRepository.findBySlug(slug).orElseThrow(() -> new ArticleNotFoundException("Article not found"));
        Profile currentProfile = profileService.getProfile(null, currentUser);
        return articleRepository.save(article.removeLike(currentProfile));
    }

    public List<Article> getFeed(String currentUser) {
        Profile currentProfile = profileService.getProfile(null, currentUser);
        List<Article> feedArticles = articleRepository.findFeed(currentProfile.getId());
        feedArticles.stream().forEach(a -> a.setAuthor(profileService.getProfile(currentUser, a.getAuthor().getProfileName())));
        return feedArticles;
    }
}
