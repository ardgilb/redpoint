package com.nashss.se.redpoint.models;

import java.time.LocalDate;
import java.time.ZonedDateTime;
public class LogbookEntryModel implements Comparable<LogbookEntryModel> {
    private String userId;

    private String date;
    private String climbId;
    private String notes;
    //CHECKSTYLE:OFF:Builder
    public LogbookEntryModel(String userId, String date, String climbId, String notes) {
        this.date = date;
        this.userId = userId;
        this.climbId = climbId;
        this.notes = notes;
    }

    public String getDate() {
        return date;
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
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public int compareTo(LogbookEntryModel other) {
        return LocalDate.parse(this.date).compareTo(LocalDate.parse(other.date));
    }

    public static class Builder {
        private String userId;
        private String date;
        private String climbId;
        private String notes;

        public Builder withDate(String date) {
            this.date = date;
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

        public Builder withNotes(String notes) {
            this.notes = notes;
            return this;
        }

        public LogbookEntryModel build() {
            return new LogbookEntryModel(userId, date, climbId, notes);
        }
    }
}

