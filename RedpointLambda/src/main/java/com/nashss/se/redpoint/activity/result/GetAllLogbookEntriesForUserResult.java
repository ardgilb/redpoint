package com.nashss.se.redpoint.activity.result;

import com.nashss.se.redpoint.models.LogbookEntryModel;

import java.util.List;

public class GetAllLogbookEntriesForUserResult {
    private final List<LogbookEntryModel> entryList;

    private GetAllLogbookEntriesForUserResult(List<LogbookEntryModel> entryList) {
        this.entryList = entryList;
    }

    public List<LogbookEntryModel> getLogbookEntry() {
        return this.entryList;
    }

    @Override
    public String toString() {
        return "GetAllLogbookEntriesForUserResult{" +
            "entryList=" + entryList +
            '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<LogbookEntryModel> entryList;

        public Builder withLogbookEntryList(List<LogbookEntryModel> entryList) {
            this.entryList = entryList;
            return this;
        }

        public GetAllLogbookEntriesForUserResult build() {
            return new GetAllLogbookEntriesForUserResult(entryList);
        }
    }
}


