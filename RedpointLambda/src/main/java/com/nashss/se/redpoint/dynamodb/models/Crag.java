package com.nashss.se.redpoint.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

import java.util.Set;

/**
 * Represents a record in the crags table.
 */
public class Crag {
    private String areaId;
    private String cragId;
    private String name;
    private String directions;
    private String description;
    private Set<String> climbs;
    @DynamoDBHashKey(attributeName = "areaId")
    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    @DynamoDBRangeKey(attributeName = "cragId")
    public String getCragId() {
        return cragId;
    }

    public void setCragId(String cragId) {
        this.cragId = cragId;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName =  "directions")
    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @DynamoDBAttribute(attributeName = "climbs")
    public Set<String> getClimbs() {
        return climbs;
    }

    public void setClimbs(Set<String> climbs) {
        this.climbs = climbs;
    }
}
