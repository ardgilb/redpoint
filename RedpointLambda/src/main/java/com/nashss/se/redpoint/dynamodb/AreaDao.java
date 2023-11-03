package com.nashss.se.redpoint.dynamodb;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2CustomAuthorizerEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nashss.se.redpoint.dynamodb.models.Area;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.nashss.se.redpoint.dynamodb.models.AreaQL;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import javax.inject.Singleton;

/**
 * Accesses data for an area using {@link Area} to represent the model in DynamoDB.
 */
@Singleton
public class AreaDao {
    private final DynamoDBMapper mapper;
    private final HttpClient client;
    private final String serviceUrl = "https://stg-api.openbeta.io";

    /**
     * Instantiates a AreaDao object.
     *
     * @param mapper the {@link DynamoDBMapper} used to interact with the areas table
     * @param client the {@link HttpClient} used to interact with the OpenBeta API
     */
    public AreaDao(DynamoDBMapper mapper, HttpClient client) {
        this.mapper = mapper;
        this.client = client;
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
    public List<AreaQL> getAllAreasFromQuery(String query) throws IOException, InterruptedException {
        String body = bodyBuilder(query);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(serviceUrl))
            .header("content-type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        List<AreaQL> areaList = getListFromResponse(response);
        //if list is empty, throw appropriate exception
        //log appropriate metrics
        return areaList;
    }
    private String bodyBuilder(String query) {
        return "{\"query\":\"query { areas(filter: {area_name: {match: \\\"" +
            query + "\\\"}}) { areaName uuid children { areaName uuid children { areaName uuid } } } }\"}";

    }
    private List<AreaQL> getListFromResponse(HttpResponse<String> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.body());

        JsonNode areasNode = rootNode.get("data").get("areas");
        String trimmedJson = objectMapper.writeValueAsString(areasNode);
        return objectMapper.readValue(trimmedJson, new TypeReference<List<AreaQL>>() {});
    }
}
