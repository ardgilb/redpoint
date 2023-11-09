package com.nashss.se.redpoint.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
@JsonDeserialize(builder = CreateCommentRequest.Builder.class)
public class CreateCommentRequest {
    private final String userId;
    private final String climbId;
    private final String text;

    private CreateCommentRequest(String userId, String climbId, String text) {
        this.userId = userId;
        this.climbId = climbId;
        this.text = text;
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

    @Override
    public String toString() {
        return "CreateCommentRequest{" +
            "userId='" + userId + '\'' +
            ", climbId='" + climbId + '\'' +
            ", text='" + text + '\'' +
            '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        private String climbId;
        private String text;

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

        public CreateCommentRequest build() {
            return new CreateCommentRequest(userId, climbId, text);
        }
    }
}

