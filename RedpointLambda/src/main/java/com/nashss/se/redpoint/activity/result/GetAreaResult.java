package com.nashss.se.redpoint.activity.result;

import com.nashss.se.redpoint.dataaccess.models.Area;

public class GetAreaResult {
    private final Area area;

    private GetAreaResult(Area area) {
        this.area = area;
    }

    public Area getArea() {
        return this.area;
    }

    @Override
    public String toString() {
        return "GetAreaResult{" +
            "area=" + area +
            '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Area area;

        public Builder withArea(Area area) {
            this.area = area;
            return this;
        }

        public GetAreaResult build() {
            return new GetAreaResult(area);
        }
    }
}
