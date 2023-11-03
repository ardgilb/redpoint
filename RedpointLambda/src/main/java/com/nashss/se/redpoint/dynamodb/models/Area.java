package com.nashss.se.redpoint.dynamodb.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = false)
public class Area {
    @JsonProperty("areaName")
    private String areaName;
    @JsonProperty("uuid")
    private String uuid;
    @JsonProperty("climbs")
    private List<Climb> climbs;
    @JsonProperty("children")
    private List<Area> children;

    public String getAreaName() {
        return areaName;
    }

    private void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getUuid() {
        return uuid;
    }

    private void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<Climb> getClimbs() {
        return climbs;
    }

    public void setClimbs(List<Climb> climbs) {
        this.climbs = climbs;
    }

    public List<Area> getChildren() {
        return children;
    }

    private void setChildren(List<Area> children) {
        this.children = children;
    }
}
