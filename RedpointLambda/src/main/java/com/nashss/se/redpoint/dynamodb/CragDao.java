package com.nashss.se.redpoint.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Singleton;

/**
 * Accesses data for a crag using {@link Crag} to represent the model in DynamoDB.
 */
@Singleton
public class CragDao {
    private final DynamoDBMapper mapper;

    /**
     * Instantiates a CragDao object.
     *
     * @param mapper the {@link DynamoDBMapper} used to interact with the crags table
     */
    public CragDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }
}
