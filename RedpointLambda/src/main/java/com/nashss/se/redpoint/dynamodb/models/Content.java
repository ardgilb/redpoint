package com.nashss.se.redpoint.dynamodb.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Content {
    @JsonProperty("location")
    private String location;
    @JsonProperty("protection")
    private String protection;
    @JsonProperty("description")
    private String description;
}
