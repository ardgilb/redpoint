package com.nashss.se.redpoint.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.Set;

/**
 * Represents a record in the climbs table.
 */

public class Climb {
    private String cragId;
    private String climbId;
    private String name;
    private Double stars;
    private Integer numRatings;
    private String location;
    private String description;
    private String rating;
    private String protection;
    private Set<String> comments;
    @DynamoDBHashKey(attributeName = "cragId")
    public String getCragId() {
        return cragId;
    }

    public void setCragId(String cragId) {
        this.cragId = cragId;
    }
    @DynamoDBRangeKey(attributeName = "climbId")
    public String getClimbId() {
        return climbId;
    }

    public void setClimbId(String climbId) {
        this.climbId = climbId;
    }
    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @DynamoDBAttribute(attributeName = "stars")
    public Double getStars() {
        return stars;
    }

    public void setStars(Double stars) {
        this.stars = stars;
    }
    @DynamoDBAttribute(attributeName = "numRatings")
    public Integer getNumRatings() {
        return numRatings;
    }
    public void setNumRatings(Integer numRatings) {
        this.numRatings = numRatings;
    }
    @DynamoDBAttribute(attributeName = "location")

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @DynamoDBAttribute(attributeName = "rating")
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
    @DynamoDBAttribute(attributeName = "protection")
    public String getProtection() {
        return protection;
    }

    public void setProtection(String protection) {
        this.protection = protection;
    }
    @DynamoDBAttribute(attributeName = "comments")
    public Set<String> getComments() {
        if (comments == null) {
            return null;
        }
        return comments;
    }

    public void setComments(Set<String> comments) {
        this.comments = comments;
    }


}