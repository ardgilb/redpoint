package com.nashss.se.redpoint.activity.request;

import java.util.Objects;

public class GetAllLogbookEntriesForUserRequest {
    private final String userId;

    private GetAllLogbookEntriesForUserRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GetAllLogbookEntriesForUserRequest that = (GetAllLogbookEntriesForUserRequest) o;
        return Objects.equals(getUserId(), that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId());
    }

    @Override
    public String toString() {
        return "GetAllLogbookEntriesForUserRequest{" +
            "userId='" + userId + '\'' +
            '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userId;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public GetAllLogbookEntriesForUserRequest build() {
            return new GetAllLogbookEntriesForUserRequest(userId);
        }

    }

}

