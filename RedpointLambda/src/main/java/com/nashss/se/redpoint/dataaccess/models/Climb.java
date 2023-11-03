package com.nashss.se.redpoint.dataaccess.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a record in the climbs table.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Climb {
    @JsonProperty("name")
    private String name;
    @JsonProperty("uuid")
    private String uuid;
    @JsonProperty("yds")
    private String grade;
    @JsonProperty("content")
    private Content content;

}
