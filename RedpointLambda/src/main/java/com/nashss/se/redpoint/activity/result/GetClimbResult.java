package com.nashss.se.redpoint.activity.result;

import com.nashss.se.redpoint.dataaccess.models.Climb;

public class GetClimbResult {
    private final Climb climb;

    private GetClimbResult(Climb climb) {
        this.climb = climb;
    }

    public Climb getClimb() {
        return this.climb;
    }

    @Override
    public String toString() {
        return "GetClimbResult{" +
            "climb=" + climb +
            '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Climb climb;

        public Builder withClimb(Climb climb) {
            this.climb = climb;
            return this;
        }

        public GetClimbResult build() {
            return new GetClimbResult(climb);
        }
    }
}
