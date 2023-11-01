package com.nashss.se.redpoint.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Singleton;
/**
 * Accesses data for an area using {@link Area} to represent the model in DynamoDB.
 */
@Singleton
public class AreaDao {
    private final DynamoDBMapper mapper;

    /**
     * Instantiates a AreaDao object.
     *
     * @param mapper the {@link DynamoDBMapper} used to interact with the areas table
     */
    public AreaDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }
}
