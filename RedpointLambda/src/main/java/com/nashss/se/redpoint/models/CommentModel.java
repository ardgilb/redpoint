package com.nashss.se.redpoint.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZonedDateTime;

public class CommentModel {

    private String timeStamp;
    private String userId;
    private String climbId;
    private String text;

    public CommentModel(String timeStamp, String userId, String climbId, String text) {
        this.timeStamp = timeStamp;
        this.userId = userId;
        this.climbId = climbId;
        this.text = text;
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
    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String timeStamp;
        private String userId;
        private String climbId;
        private String text;

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
            return new CommentModel(timeStamp, userId, climbId, text);
        }
    }
}
