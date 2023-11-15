package com.nashss.se.redpoint.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
@JsonDeserialize(builder = DeleteLogbookEntryRequest.Builder.class)
public class DeleteLogbookEntryRequest {
    private final String climbId;
    private final String userId;

    private DeleteLogbookEntryRequest(String userId, String climbId) {
        this.userId = userId;
        this.climbId = climbId;
    }

    public String getClimbId() {
        return climbId;
    }

    public String getUserId() {
        return userId;
    }

    //CHECKSTYLE:OFF:Builder
    public static DeleteLogbookEntryRequest.Builder builder() {
        return new DeleteLogbookEntryRequest.Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        private String climbId;

        public DeleteLogbookEntryRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }
        public DeleteLogbookEntryRequest.Builder withClimbId(String climbId) {
            this.climbId = climbId;
            return this;
        }
        public DeleteLogbookEntryRequest build() {
            return new DeleteLogbookEntryRequest(userId, climbId);
        }
    }
}

