package com.nashss.se.redpoint.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Singleton;

/**
 * Accesses data for a climb using {@link Climb} to represent the model in DynamoDB.
 */
@Singleton
public class ClimbDao {
    private final DynamoDBMapper mapper;

    /**
     * Instantiates a ClimbDao object.
     *
     * @param mapper the {@link DynamoDBMapper} used to interact with the climbs table
     */
    public ClimbDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }
}
