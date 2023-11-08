package com.nashss.se.redpoint.activity.request;

import java.util.Objects;

public class GetAreaRequest {
    private final String uuid;

    private GetAreaRequest(String uuid) {
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
        GetAreaRequest that = (GetAreaRequest) o;
        return Objects.equals(getUuid(), that.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid());
    }

    @Override
    public String toString() {
        return "GetAreaRequest{" +
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

        public GetAreaRequest build() {
            return new GetAreaRequest(uuid);
        }

    }
}

