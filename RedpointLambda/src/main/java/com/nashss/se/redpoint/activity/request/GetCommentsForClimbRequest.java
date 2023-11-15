package com.nashss.se.redpoint.activity.request;

import java.util.Objects;

public class GetCommentsForClimbRequest {
    private final String climbId;

    private GetCommentsForClimbRequest(String climbId) {
        this.climbId = climbId;
    }

    public String getClimbId() {
        return climbId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GetCommentsForClimbRequest that = (GetCommentsForClimbRequest) o;
        return Objects.equals(getClimbId(), that.getClimbId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClimbId());
    }

    @Override
    public String toString() {
        return "GetCommentsForClimbRequest{" +
            "climbId='" + climbId + '\'' +
            '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String climbId;

        public Builder withClimbId(String climbId) {
            this.climbId = climbId;
            return this;
        }

        public GetCommentsForClimbRequest build() {
            return new GetCommentsForClimbRequest(climbId);
        }

    }

}
