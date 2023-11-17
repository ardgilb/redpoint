package com.nashss.se.redpoint.models;

import java.time.ZonedDateTime;
public class CommentModel implements Comparable<CommentModel> {
    private String commentId;

    private String timeStamp;
    private String userId;
    private String climbId;
    private String text;
    //CHECKSTYLE:OFF:Builder
    public CommentModel(String commentId, String timeStamp, String userId, String climbId, String text) {
        this.commentId = commentId;
        this.timeStamp = timeStamp;
        this.userId = userId;
        this.climbId = climbId;
        this.text = text;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getUserId() {
        return userId;
    }

    public String getClimbId() {
        return climbId;
    }

    public String getText() {
        return text;
    }
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public int compareTo(CommentModel other) {
        return ZonedDateTime.parse(this.timeStamp).compareTo(ZonedDateTime.parse(other.timeStamp));
    }

    public static class Builder {
        private String commentId;
        private String timeStamp;
        private String userId;
        private String climbId;
        private String text;
        public Builder withCommentId(String commentId) {
            this.commentId = commentId;
            return this;
        }

        public Builder withTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withClimbId(String climbId) {
            this.climbId = climbId;
            return this;
        }

        public Builder withText(String text) {
            this.text = text;
            return this;
        }

        public CommentModel build() {
            return new CommentModel(commentId, timeStamp, userId, climbId, text);
        }
    }
}
