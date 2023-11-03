package com.nashss.se.redpoint.dataaccess;

import com.nashss.se.redpoint.dataaccess.models.Area;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import javax.inject.Inject;
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
    @Inject
    public AreaDao(DynamoDBMapper mapper, HttpClient client) {
        this.mapper = mapper;
        this.client = client;
    }
    /**
     * Returns the {@link Area} corresponding to the specified areaId.
     *
     * @param uuid the Areas's uuid
     * @return the stored Area, or throws exception if none was found.
     */
    public Area getArea(String uuid) throws IOException, InterruptedException {
        String body = "{\"query\":\"query { area(uuid: \\\"" +
            uuid + "\\\") { areaName climbs { name uuid yds } children { areaName uuid } } }\"}";

        HttpRequest request = httpRequestBuilder(body);

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return getAreaFromHttpResponse(response);
    }
    /**
     * Returns the list of all Areas in the database.
     *
     * @param query the query from the front end.
     * @return the stored areas, or none if none were found.
     */
    public List<Area> getAllAreasFromQuery(String query) throws IOException, InterruptedException {
        String body = "{\"query\":\"query { areas(filter: {area_name: {match: \\\"" +
            query + "\\\"}}) { areaName uuid climbs { name uuid } children { areaName uuid } } }\"}";

        HttpRequest request = httpRequestBuilder(body);

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        List<Area> areaList = getListFromHttpResponse(response);
        //if list is empty, throw appropriate exception
        //log appropriate metrics
        return areaList;
    }
    private List<Area> getListFromHttpResponse(HttpResponse<String> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.body());
        JsonNode areasNode = rootNode.get("data").get("areas");
        String trimmedJson = objectMapper.writeValueAsString(areasNode);
        return objectMapper.readValue(trimmedJson, new TypeReference<List<Area>>() { });
    }
    private Area getAreaFromHttpResponse(HttpResponse<String> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.body());
        JsonNode areaNode = rootNode.get("data").get("area");
        String trimmedJson = objectMapper.writeValueAsString(areaNode);
        return objectMapper.readValue(trimmedJson, Area.class);
    }
    private HttpRequest httpRequestBuilder(String body) {
        return HttpRequest.newBuilder()
            .uri(URI.create(serviceUrl))
            .header("content-type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();
    }
}
