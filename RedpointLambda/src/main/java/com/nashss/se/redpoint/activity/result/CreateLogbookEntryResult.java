package com.nashss.se.redpoint.activity.result;

import com.nashss.se.redpoint.models.LogbookEntryModel;

public class CreateLogbookEntryResult {
    private final LogbookEntryModel entry;

    private CreateLogbookEntryResult(LogbookEntryModel entry) {
        this.entry = entry;
    }

    public LogbookEntryModel getLogbookEntry() {
        return entry;
    }

    @Override
    public String toString() {
        return "CreateLogbookEntryResult{" +
            "entry=" + entry +
            '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private LogbookEntryModel entry;

        public Builder withLogbookEntry(LogbookEntryModel entry) {
            this.entry = entry;
            return this;
        }

        public CreateLogbookEntryResult build() {
            return new CreateLogbookEntryResult(entry);
        }
    }
}

