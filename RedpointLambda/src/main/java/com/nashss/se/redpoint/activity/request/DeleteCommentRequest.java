package com.nashss.se.redpoint.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
@JsonDeserialize(builder = DeleteCommentRequest.Builder.class)
public class DeleteCommentRequest {
    private final String commentId;
    private final String userId;

    private DeleteCommentRequest(String commentId, String userId) {
        this.commentId = commentId;
        this.userId = userId;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getUserId() {
        return userId;
    }
    //CHECKSTYLE:OFF:Builder
    public static DeleteCommentRequest.Builder builder() {
        return new DeleteCommentRequest.Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String commentId;
        private String userId;

        public DeleteCommentRequest.Builder withCommentId(String commentId) {
            this.commentId = commentId;
            return this;
        }
        public DeleteCommentRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }
        public DeleteCommentRequest build() {
            return new DeleteCommentRequest(commentId, userId);
        }
    }
}
