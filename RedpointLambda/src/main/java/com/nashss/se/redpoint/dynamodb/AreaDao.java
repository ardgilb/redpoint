package com.nashss.se.redpoint.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.nashss.se.redpoint.dynamodb.models.Area;

import javax.inject.Singleton;
import java.util.List;

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
    /**
     * Returns the {@link Area} corresponding to the specified areaId.
     *
     * @param areaId the Areas's areaId
     * @return the stored Area, or throws exception if none was found.
     */
    public Area getArea(String areaId) {
        Area area = this.mapper.load(Area.class, areaId);
        //if area == null, throw new AreaNotFoundException();
        //log appropriate metrics later
        return area;
    }
    /**
     * Returns the list of all Areas in the database.
     *
     * @return the stored areas, or none if none were found.
     */
    public List<Area> getAllAreas() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<Area> areaList = mapper.scan(Area.class, scanExpression);
        //if list is empty, throw appropriate exception
        //log appropriate metrics
        return areaList;
    }
}
