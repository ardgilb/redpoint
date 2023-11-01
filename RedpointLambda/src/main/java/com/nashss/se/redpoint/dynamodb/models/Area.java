package com.nashss.se.redpoint.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;

import java.util.List;
import java.util.Set;

/**
 * Represents a record in the areas table.
 */

public class Area {
    private String areaId;
    private String name;
    private List<String> coordinates;
    private String description;
    private String directions;
    private Set<String> crags;
    @DynamoDBHashKey(attributeName = "areaId")
    public String getAreaId() {
        return areaId;
    }
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "coordinates")
    public List<String> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<String> coordinates) {
        this.coordinates = coordinates;
    }

    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @DynamoDBAttribute(attributeName = "directions")
    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    @DynamoDBAttribute(attributeName = "crags")
    public Set<String> getCrags() {
        return crags;
    }

    public void setCrags(Set<String> crags) {
        this.crags = crags;
    }
}
