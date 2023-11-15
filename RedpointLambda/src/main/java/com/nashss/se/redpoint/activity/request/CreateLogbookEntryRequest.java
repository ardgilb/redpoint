package com.nashss.se.redpoint.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDate;

@JsonDeserialize(builder = CreateLogbookEntryRequest.Builder.class)
public class CreateLogbookEntryRequest {
    private final String userId;
    private final String date;
    private final String climbId;
    private final String notes;
    private CreateLogbookEntryRequest(String userId, String climbId, String notes, String date) {
        this.userId = userId;
        this.climbId = climbId;
        this.date = date;
        this.notes = notes;
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
        return "CreateLogbookEntryRequest{" +
            "userId='" + userId + '\'' +
            ", date=" + date +
            ", climbId='" + climbId + '\'' +
            ", notes='" + notes + '\'' +
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
        private String date;
        private String notes;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }
        public Builder withClimbId(String climbId) {
            this.climbId = climbId;
            return this;
        }
        public Builder withDate(String date) {
            this.date = date;
            return this;
        }
        public Builder withNotes(String notes) {
            this.notes = notes;
            return this;
        }

        public CreateLogbookEntryRequest build() {
            return new CreateLogbookEntryRequest(userId, climbId, notes, date);
        }
    }
}

