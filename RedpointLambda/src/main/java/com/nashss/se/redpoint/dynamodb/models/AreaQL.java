package com.nashss.se.redpoint.dynamodb.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = false)
public class AreaQL {
    @JsonProperty("areaName")
    private String areaName;
    @JsonProperty("uuid")
    private String uuid;
    @JsonProperty("children")
    private List<AreaQL> children;

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

    public List<AreaQL> getChildren() {
        return children;
    }

    public void setChildren(List<AreaQL> children) {
        this.children = children;
    }
}
