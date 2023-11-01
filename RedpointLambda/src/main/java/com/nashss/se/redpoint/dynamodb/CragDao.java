package com.nashss.se.redpoint.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.nashss.se.redpoint.dynamodb.models.Climb;
import com.nashss.se.redpoint.dynamodb.models.Crag;

import javax.inject.Singleton;
import java.util.List;

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
    /**
     * Returns the {@link Crag} corresponding to the specified cragId.
     *
     * @param cragId the Crag's cragId
     * @return the stored Crag, or throws exception if none was found.
     */
    public Climb getCrag(String cragId) {
        //get the crag using the gsi where we only take the cragId
        //if crag == null, throw new CragNotFoundException();
        //log appropriate metrics later
        //return the crag
        return null;
    }
    /**
     * Returns the list of all Crags at a given Area.
     *
     * @param areaId the areaId to find all the crags for
     * @return the stored crags corresponding to the areaId, or none if none were found.
     */
    public List<Crag> getAllCragsForArea(String areaId) {
        Crag crag = new Crag();
        crag.setAreaId(areaId);

        DynamoDBQueryExpression<Crag> queryExpression = new DynamoDBQueryExpression<Crag>()
            .withHashKeyValues(crag);

        PaginatedQueryList<Crag> cragList = mapper.query(Crag.class, queryExpression);
        //if list is empty, throw appropriate exception
        //log appropriate metrics
        return cragList;
    }
}
