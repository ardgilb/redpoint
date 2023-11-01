package com.nashss.se.redpoint.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.nashss.se.redpoint.dynamodb.models.Area;
import com.nashss.se.redpoint.dynamodb.models.Climb;

import javax.inject.Singleton;
import java.util.List;

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

    /**
     * Returns the {@link Climb} corresponding to the specified climbId.
     *
     * @param climbId the Climb's climbId
     * @return the stored Climb, or throws exception if none was found.
     */
    public Climb getClimb(String climbId) {
        //get the climb using the gsi where we only take the climbId
        //if climb == null, throw new ClimbNotFoundException();
        //log appropriate metrics later
        //return the climb
        return null;
    }
    /**
     * Returns the list of all Climbs at a given Crag.
     *
     * @param cragId the cragId to find all the climbs for
     * @return the stored climbs corresponding to the cragId, or none if none were found.
     */
    public List<Climb> getAllClimbsForCrag(String cragId) {
        Climb climb = new Climb();
        climb.setCragId(cragId);

        DynamoDBQueryExpression<Climb> queryExpression = new DynamoDBQueryExpression<Climb>()
            .withHashKeyValues(climb);

        PaginatedQueryList<Climb> climbList = mapper.query(Climb.class, queryExpression);
        //if list is empty, throw appropriate exception
        //log appropriate metrics
        return climbList;
    }
}
