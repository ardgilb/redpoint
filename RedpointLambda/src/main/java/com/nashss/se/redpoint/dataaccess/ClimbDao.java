package com.nashss.se.redpoint.dataaccess;

import com.nashss.se.redpoint.dataaccess.models.Climb;

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
 * Accesses data for a climb using {@link Climb}.
 */
@Singleton
public class ClimbDao {
    private final HttpClient client;
    private final String serviceUrl = "https://stg-api.openbeta.io";

    /**
     * Instantiates a ClimbDao object.
     *
     * @param client the {@link HttpClient} used to interact with the OpenBeta API
     */
    public ClimbDao(HttpClient client) {
        this.client = client;
    }

    /**
     * Returns the {@link Climb} corresponding to the specified climbId.
     *
     * @param uuid the Climb's uuid
     * @return the Climb, or throws exception if none was found.
     */
    public Climb getClimb(String uuid) {
        String body = "{\"query\":\"query MyQuery { climb(uuid: \\\"" +
            uuid + "\\\") { name yds uuid metadata { leftRightIndex }" +
            "content { description location protection } } }\"}";

        HttpRequest request = httpRequestBuilder(body);
        HttpResponse response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("getClimb client.send failed" + e.getStackTrace());
        }
        return getClimbFromHttpResponse(response);
    }
    private HttpRequest httpRequestBuilder(String body) {
        return HttpRequest.newBuilder()
            .uri(URI.create(serviceUrl))
            .header("content-type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();
    }
    private Climb getClimbFromHttpResponse(HttpResponse<String> response) {
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
}
