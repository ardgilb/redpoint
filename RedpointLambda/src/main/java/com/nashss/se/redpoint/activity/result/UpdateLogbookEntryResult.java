package com.nashss.se.redpoint.activity.result;

import com.nashss.se.redpoint.models.LogbookEntryModel;

public class UpdateLogbookEntryResult {
    private final LogbookEntryModel entry;

    private UpdateLogbookEntryResult(LogbookEntryModel entry) {
        this.entry = entry;
    }

    public LogbookEntryModel getLogbookEntry() {
        return entry;
    }

    @Override
    public String toString() {
        return "UpdateLogbookEntryResult{" +
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

        public UpdateLogbookEntryResult build() {
            return new UpdateLogbookEntryResult(entry);
        }
    }
}


