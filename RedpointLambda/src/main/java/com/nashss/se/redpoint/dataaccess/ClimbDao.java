package com.nashss.se.redpoint.dataaccess;

import com.nashss.se.redpoint.dataaccess.models.Climb;
import com.nashss.se.redpoint.exceptions.ClimbNotFoundException;
import com.nashss.se.redpoint.metrics.MetricsConstants;
import com.nashss.se.redpoint.metrics.MetricsPublisher;
import com.nashss.se.redpoint.utils.HttpUtils;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Accesses data for a climb using {@link Climb}.
 */
@Singleton
public class ClimbDao {
    private final MetricsPublisher metricsPublisher;
    private final HttpClient client;

    /**
     * Instantiates a ClimbDao object.
     *
     * @param client the {@link HttpClient} used to interact with the OpenBeta API
     * @param metricsPublisher the {@link MetricsPublisher} used to publish metrics to CloudWatch
     */
    @Inject
    public ClimbDao(HttpClient client, MetricsPublisher metricsPublisher) {
        this.client = client;
        this.metricsPublisher = metricsPublisher;
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

        HttpRequest request = HttpUtils.httpRequestBuilder(body);
        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("getClimb client.send failed" + e.getStackTrace());
        }
        Climb climb = HttpUtils.getClimbFromHttpResponse(response);
        if (climb == null) {
            metricsPublisher.addCount(MetricsConstants.GETCLIMB_CLIMBNOTFOUND_COUNT, 1);
            throw new ClimbNotFoundException("Could not find climb with id: " + uuid);
        }
        metricsPublisher.addCount(MetricsConstants.GETCLIMB_CLIMBNOTFOUND_COUNT, 0);
        return climb;
    }
}
