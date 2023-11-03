package com.nashss.se.redpoint.activity.result;

import com.nashss.se.redpoint.dataaccess.models.Area;

import java.util.List;

public class GetAllAreasResult {
    private final List<Area> areaList;

    private GetAllAreasResult(List<Area> areaList) {
        this.areaList = areaList;
    }

    public List<Area> getArea() {
        return this.areaList;
    }

    @Override
    public String toString() {
        return "GetAllAreasResult{" +
            "areaList=" + areaList +
            '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<Area> areaList;

        public Builder withAreaList(List<Area> areaList) {
            this.areaList = areaList;
            return this;
        }

        public GetAllAreasResult build() {
            return new GetAllAreasResult(areaList);
        }
    }
}

