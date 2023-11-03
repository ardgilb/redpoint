package com.nashss.se.redpoint.dynamodb.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = false)
public class AreaQL {
    @JsonProperty("areaName")
    private String areaName;

    @JsonProperty("children")
    private List<AreaQL> children;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public List<AreaQL> getChildren() {
        if (children == null) {
            // Ensure that the "children" field is never null, initialize as an empty list
            children = new ArrayList<>();
        }
        return children;
    }

    public void setChildren(List<AreaQL> children) {
        this.children = children;
    }
}
