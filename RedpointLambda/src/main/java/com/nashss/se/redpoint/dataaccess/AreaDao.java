package com.nashss.se.redpoint.dataaccess;

import com.nashss.se.redpoint.dataaccess.models.Area;

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
 * Accesses data for an area using {@link Area}.
 */
@Singleton
public class AreaDao {
    private final HttpClient client;
    private final String serviceUrl = "https://stg-api.openbeta.io";

    /**
     * Instantiates a AreaDao object.
     *
     * @param client the {@link HttpClient} used to interact with the OpenBeta API
     */
    @Inject
    public AreaDao(HttpClient client) {
        this.client = client;
    }
    /**
     * Returns the {@link Area} corresponding to the specified areaId.
     *
     * @param uuid the Areas's uuid
     * @return the stored Area, or throws exception if none was found.
     */
    public Area getArea(String uuid) {
        String body = "{\"query\":\"query { area(uuid: \\\"" +
            uuid + "\\\") { areaName content { description } metadata{ lat lng }" +
            "climbs { name uuid yds } children { areaName uuid } } }\"}";

        HttpRequest request = httpRequestBuilder(body);
        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("getArea client.send failed" + e.getMessage());
        }

        return getAreaFromHttpResponse(response);
    }
    /**
     * Returns the list of all Areas matching the query.
     *
     * @param query the query from the front end.
     * @return the stored areas, or none if none were found.
     */
    public List<Area> getAllAreasFromQuery(String query) {
        String body = "{\"query\":\"query { areas(filter: {area_name: {match: \\\"" +
            query + "\\\"}}) { areaName uuid climbs { name uuid yds } children { areaName uuid } } }\"}";

        HttpRequest request = httpRequestBuilder(body);

        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("getAllAreasFromQuery client.send failed" + e.getStackTrace());
        }
        return getListFromHttpResponse(response);
    }
    private List<Area> getListFromHttpResponse(HttpResponse<String> response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.body());
            JsonNode areasNode = rootNode.get("data").get("areas");
            String trimmedJson = objectMapper.writeValueAsString(areasNode);
            return objectMapper.readValue(trimmedJson, new TypeReference<List<Area>>() { });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json Processing failed for getAllAreasFromQuery" + e.getStackTrace());
        }
    }
    private Area getAreaFromHttpResponse(HttpResponse<String> response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.body());
            JsonNode areaNode = rootNode.get("data").get("area");
            String trimmedJson = objectMapper.writeValueAsString(areaNode);
            return objectMapper.readValue(trimmedJson, Area.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json Processing failed for getArea" + e.getStackTrace());
        }

    }
    private HttpRequest httpRequestBuilder(String body) {
        return HttpRequest.newBuilder()
            .uri(URI.create(serviceUrl))
            .header("content-type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();
    }
}
