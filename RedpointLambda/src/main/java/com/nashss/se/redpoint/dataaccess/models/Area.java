package com.nashss.se.redpoint.dataaccess.models;

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
    @JsonProperty("content")
    private Content content;
    @JsonProperty("metadata")
    private Coordinates coordinates;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
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

    public void setChildren(List<Area> children) {
        this.children = children;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
