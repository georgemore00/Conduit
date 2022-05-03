package com.george.conduit.dto.comment;

import java.util.List;

public class MultipleCommentsResponse {

    private List<SingleCommentResponse> comments;

    public MultipleCommentsResponse(List<SingleCommentResponse> comments) {
        this.comments = comments;
    }

    public List<SingleCommentResponse> getComments() {
        return comments;
    }

    public void setComments(List<SingleCommentResponse> comments) {
        this.comments = comments;
    }
}
