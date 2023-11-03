package com.nashss.se.redpoint.dynamodb;

import com.nashss.se.redpoint.dynamodb.models.Climb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.inject.Singleton;

/**
 * Accesses data for a climb using {@link Climb} to represent the model in DynamoDB.
 */
@Singleton
public class ClimbDao {
    private final DynamoDBMapper mapper;
    private final HttpClient client;
    private final String serviceUrl = "https://stg-api.openbeta.io";

    /**
     * Instantiates a ClimbDao object.
     *
     * @param client the {@link HttpClient} used to interact with the OpenBeta API
     * @param mapper the {@link DynamoDBMapper} used to interact with the climbs table
     */
    public ClimbDao(DynamoDBMapper mapper, HttpClient client) {
        this.mapper = mapper;
        this.client = client;
    }

    /**
     * Returns the {@link Climb} corresponding to the specified climbId.
     *
     * @param uuid the Climb's uuid
     * @return the Climb, or throws exception if none was found.
     */
    public Climb getClimb(String uuid) throws IOException, InterruptedException {
        String body = "{\"query\":\"query MyQuery { climb(uuid: \\\"" +
            uuid + "\\\") { name yds uuid content { description location protection } } }\"}";

        HttpRequest request = httpRequestBuilder(body);

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return getClimbFromHttpResponse(response);
    }
    private HttpRequest httpRequestBuilder(String body) {
        return HttpRequest.newBuilder()
            .uri(URI.create(serviceUrl))
            .header("content-type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();
    }
    private Climb getClimbFromHttpResponse(HttpResponse<String> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.body());
        JsonNode climbNode = rootNode.get("data").get("climb");
        String trimmedJson = objectMapper.writeValueAsString(climbNode);
        return objectMapper.readValue(trimmedJson, Climb.class);
    }
}
