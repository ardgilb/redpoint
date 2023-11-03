package com.nashss.se.redpoint.graphql;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nashss.se.redpoint.dynamodb.models.AreaQL;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GraphQLClient {

    private static String serviceUrl = "https://stg-api.openbeta.io";

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        String body = "{\"query\":\"query { areas(filter: {area_name: {match: \\\"Foster Falls\\\"}}) { areaName children { areaName children { areaName } } } }\"}";

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(serviceUrl))
            .header("content-type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.body());

        JsonNode areasNode = rootNode.get("data").get("areas");
        String trimmedJson = objectMapper.writeValueAsString(areasNode);
        trimmedJson = trimmedJson.substring(1, trimmedJson.length());
        AreaQL areaQL = objectMapper.readValue(trimmedJson, AreaQL.class);
    }
}

