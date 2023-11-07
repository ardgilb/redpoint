package com.nashss.se.redpoint.activity.request;

import java.util.Objects;

public class GetAllAreasRequest {
    private final String query;

    private GetAllAreasRequest(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GetAllAreasRequest that = (GetAllAreasRequest) o;
        return Objects.equals(getQuery(), that.getQuery());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuery());
    }

    @Override
    public String toString() {
        return "GetAllAreasRequest{" +
            "query='" + query + '\'' +
            '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String query;

        public Builder withQuery(String query) {
            this.query = query;
            return this;
        }

        public GetAllAreasRequest build() {
            return new GetAllAreasRequest(query);
        }

    }
}

