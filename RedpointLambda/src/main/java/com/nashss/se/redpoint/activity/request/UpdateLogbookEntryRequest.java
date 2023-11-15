package com.nashss.se.redpoint.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = UpdateLogbookEntryRequest.Builder.class)
public class UpdateLogbookEntryRequest {
    private final String userId;
    private final String climbId;
    private final String notes;
    private final String date;
    private UpdateLogbookEntryRequest(String userId, String climbId, String notes, String date) {
        this.userId = userId;
        this.climbId = climbId;
        this.notes = notes;
        this.date = date;
    }



    public String getUserId() {
        return userId;
    }

    public String getClimbId() {
        return climbId;
    }

    public String getNotes() {
        return notes;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "UpdateCommentRequest{" +
            "userId='" + userId + '\'' +
            ", climbId='" + climbId + '\'' +
            ", notes='" + notes + '\'' +
            ", date='" + date + '\'' +
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
        private String notes;
        private String date;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }
        public Builder withClimbId(String climbId) {
            this.climbId = climbId;
            return this;
        }
        public Builder withNotes(String notes) {
            this.notes = notes;
            return this;
        }
        public Builder withDate(String date) {
            this.date = date;
            return this;
        }

        public UpdateLogbookEntryRequest build() {
            return new UpdateLogbookEntryRequest(userId, climbId, notes, date);
        }
    }
}


