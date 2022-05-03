package com.george.conduit.controller;

import com.george.conduit.dto.comment.CreateCommentRequest;
import com.george.conduit.dto.comment.MultipleCommentsResponse;
import com.george.conduit.dto.comment.SingleCommentResponse;
import com.george.conduit.model.Comment;
import com.george.conduit.security.jwt.MyPrincipal;
import com.george.conduit.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.george.conduit.utils.CurrentUserInfo.getOptionalCurrentUser;

@RequestMapping(path = "api/v1/articles")
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(path = "/{slug}/comments", method = RequestMethod.POST)
    public ResponseEntity<?> addCommentToArticle(@AuthenticationPrincipal MyPrincipal myPrincipal,
                                                 @PathVariable String slug,
                                                 @Valid @RequestBody CreateCommentRequest request) {

        String currentUser = myPrincipal.getProfileName();
        Comment createdComment = commentService.addCommentToArticle(currentUser, slug, request.getBody());
        return new ResponseEntity<>(new SingleCommentResponse(createdComment), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{slug}/comments", method = RequestMethod.GET)
    public ResponseEntity<?> getComments(@AuthenticationPrincipal MyPrincipal myPrincipal, @PathVariable String slug) {

        String currentUser = getOptionalCurrentUser(myPrincipal);
        List<SingleCommentResponse> comments = commentService
                .getComments(currentUser, slug).stream()
                .map(c -> new SingleCommentResponse(c))
                .collect(Collectors.toList());
        return new ResponseEntity<>(new MultipleCommentsResponse(comments), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{slug}/comments/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteComment(@AuthenticationPrincipal MyPrincipal myPrincipal,
                                           @PathVariable String slug,
                                           @PathVariable Long id) {

        String currentUser = myPrincipal.getProfileName();
        commentService.deleteComment(currentUser, slug, id);
        return ResponseEntity.accepted().build();
    }
}
