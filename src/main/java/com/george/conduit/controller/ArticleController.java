package com.george.conduit.controller;

import com.george.conduit.dto.article.CreateArticleRequest;
import com.george.conduit.dto.article.MultipleArticlesResponse;
import com.george.conduit.dto.article.SingleArticleResponse;
import com.george.conduit.dto.article.UpdateArticleRequest;
import com.george.conduit.model.Article;
import com.george.conduit.security.jwt.MyPrincipal;
import com.george.conduit.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.george.conduit.utils.CurrentUserInfo.getOptionalCurrentUser;

@RequestMapping(path = "api/v1/articles")
@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<?> createArticle(@AuthenticationPrincipal MyPrincipal myPrincipal,
                                           @Valid @RequestBody CreateArticleRequest request) {

        String author = myPrincipal.getProfileName();
        Article newArticle = request.toArticleEntity();
        Article createdArticle = articleService.createArticle(author, newArticle);

        URI location = buildURI(createdArticle);
        return ResponseEntity.created(location).body(new SingleArticleResponse(createdArticle));
    }

    @RequestMapping(path = "/{slug}", method = RequestMethod.GET)
    public ResponseEntity<?> viewArticle(@AuthenticationPrincipal MyPrincipal myPrincipal, @PathVariable String slug) {
        String viewer = getOptionalCurrentUser(myPrincipal);
        Article article = articleService.viewArticle(viewer, slug);
        return new ResponseEntity<>(new SingleArticleResponse(article), HttpStatus.OK);
    }

    @RequestMapping(path = "/{slug}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateArticle(@AuthenticationPrincipal MyPrincipal myPrincipal,
                                           @RequestBody UpdateArticleRequest request,
                                           @PathVariable String slug) {

        String author = myPrincipal.getProfileName();
        Article newArticle = request.toArticleEntity();
        Article updatedArticle = articleService.updateArticle(author, slug, newArticle);
        return new ResponseEntity<>(new SingleArticleResponse(updatedArticle), HttpStatus.OK);
    }

    @RequestMapping(path = "/{slug}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteArticle(@AuthenticationPrincipal MyPrincipal myPrincipal, @PathVariable String slug) {
        String currentUser = myPrincipal.getProfileName();
        articleService.deleteArticle(currentUser, slug);
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(path = "/{slug}/like", method = RequestMethod.POST)
    public ResponseEntity<?> addLikeToArticle(@AuthenticationPrincipal MyPrincipal myPrincipal, @PathVariable String slug) {
        String currentUser = myPrincipal.getProfileName();
        Article updatedArticle = articleService.addLike(currentUser, slug);
        return new ResponseEntity<>(new SingleArticleResponse(updatedArticle), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{slug}/like", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeLikeFromArticle(@AuthenticationPrincipal MyPrincipal myPrincipal, @PathVariable String slug) {
        String currentUser = myPrincipal.getProfileName();
        Article updatedArticle = articleService.removeLike(currentUser, slug);
        return new ResponseEntity<>(new SingleArticleResponse(updatedArticle), HttpStatus.ACCEPTED);
    }

    @RequestMapping(path = "/feed", method = RequestMethod.GET)
    public ResponseEntity<?> getFeed(@AuthenticationPrincipal MyPrincipal myPrincipal) {
        String currentUser = myPrincipal.getProfileName();
        List<Article> articles = articleService.getFeed(currentUser);
        return new ResponseEntity<>(new MultipleArticlesResponse(articles), HttpStatus.OK);
    }

    private URI buildURI(Article createdArticle) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{slug}")
                .buildAndExpand(createdArticle.getSlug())
                .toUri();
    }
}
