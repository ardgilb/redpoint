package com.nashss.se.redpoint.dataaccess.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coordinates {
    @JsonProperty("lat")
    private String latitude;
    @JsonProperty("lng")
    private String longitude;
}
