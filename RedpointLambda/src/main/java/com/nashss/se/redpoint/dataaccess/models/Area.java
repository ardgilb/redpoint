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
}
