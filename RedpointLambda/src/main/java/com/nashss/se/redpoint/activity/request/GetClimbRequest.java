package com.nashss.se.redpoint.activity.request;

import java.util.Objects;

public class GetClimbRequest {
    private final String uuid;

    private GetClimbRequest(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GetClimbRequest that = (GetClimbRequest) o;
        return Objects.equals(getUuid(), that.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid());
    }

    @Override
    public String toString() {
        return "GetClimbRequest{" +
            "uuid='" + uuid + '\'' +
            '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String uuid;

        public Builder withUuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public GetClimbRequest build() {
            return new GetClimbRequest(uuid);
        }

    }
}


