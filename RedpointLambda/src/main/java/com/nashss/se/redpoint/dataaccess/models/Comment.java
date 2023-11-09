package com.nashss.se.redpoint.dataaccess.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.nashss.se.redpoint.converters.ZonedDateTimeConverter;

import java.time.ZonedDateTime;
@DynamoDBTable(tableName = "comments")
public class Comment {
    private static final String CLIMB_INDEX = "ClimbIdAndTimeIndex";
    @DynamoDBTypeConverted(converter = ZonedDateTimeConverter.class)
    @DynamoDBRangeKey(attributeName = "timeStamp")
    private ZonedDateTime timeStamp;
    @DynamoDBHashKey(attributeName = "userId")
    private String userId;
    @DynamoDBIndexHashKey(globalSecondaryIndexName = CLIMB_INDEX, attributeName = "climbId")
    private String climbId;
    @DynamoDBAttribute(attributeName = "text")
    private String text;

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(ZonedDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClimbId() {
        return climbId;
    }

    public void setClimbId(String climbId) {
        this.climbId = climbId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
