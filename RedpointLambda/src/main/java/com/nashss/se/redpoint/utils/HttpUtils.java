package com.nashss.se.redpoint.utils;

import com.nashss.se.redpoint.dataaccess.models.Area;
import com.nashss.se.redpoint.dataaccess.models.Climb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


public class HttpUtils {
    /**
     * The URL of the service to which HTTP requests are made.
     */
    private static final String serviceUrl = "https://stg-api.openbeta.io";
    /**
     * Parses the HTTP response body into a list of areas.
     *
     * @param response The HTTP response containing the JSON data.
     * @return A list of areas extracted from the JSON response.
     * @throws RuntimeException If JSON processing fails.
     */
    public static List<Area> getListFromHttpResponse(HttpResponse<String> response) {
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
    /**
     * Parses the HTTP response body into an area object.
     *
     * @param response The HTTP response containing the JSON data.
     * @return An area object extracted from the JSON response.
     * @throws RuntimeException If JSON processing fails.
     */
    public static Area getAreaFromHttpResponse(HttpResponse<String> response) {
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
    /**
     * Parses the HTTP response body into a climb object.
     *
     * @param response The HTTP response containing the JSON data.
     * @return A climb object extracted from the JSON response.
     * @throws RuntimeException If JSON processing fails.
     */
    public static Climb getClimbFromHttpResponse(HttpResponse<String> response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.body());
            JsonNode climbNode = rootNode.get("data").get("climb");
            String trimmedJson = objectMapper.writeValueAsString(climbNode);
            return objectMapper.readValue(trimmedJson, Climb.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json Processing failed for getClimb" + e.getStackTrace());

        }
    }
    /**
     * Builds an HTTP request with a specified request body for the OpenBeta API.
     *
     * @param body The request body containing the GraphQL query.
     * @return An HTTP request configured for the OpenBeta API.
     */
    public static HttpRequest httpRequestBuilder(String body) {
        return HttpRequest.newBuilder()
            .uri(URI.create(serviceUrl))
            .header("content-type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();
    }
}
