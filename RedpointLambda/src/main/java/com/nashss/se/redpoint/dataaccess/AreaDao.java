package com.nashss.se.redpoint.dataaccess;

import com.nashss.se.redpoint.dataaccess.models.Area;
import com.nashss.se.redpoint.exceptions.AreaNotFoundException;
import com.nashss.se.redpoint.metrics.MetricsConstants;
import com.nashss.se.redpoint.metrics.MetricsPublisher;
import com.nashss.se.redpoint.utils.HttpUtils;

import java.io.IOException;
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
    private final MetricsPublisher metricsPublisher;
    private final HttpClient client;

    /**
     * Instantiates a AreaDao object.
     *
     * @param client the {@link HttpClient} used to interact with the OpenBeta API
     * @param metricsPublisher the {@link MetricsPublisher} used to publish metrics to CloudWatch
     */
    @Inject
    public AreaDao(HttpClient client, MetricsPublisher metricsPublisher) {
        this.client = client;
        this.metricsPublisher = metricsPublisher;
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
            "climbs { name uuid yds metadata{ leftRightIndex } } children { areaName uuid } } }\"}";

        HttpRequest request = HttpUtils.httpRequestBuilder(body);
        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("getArea client.send failed" + e.getMessage());
        }
        Area area = HttpUtils.getAreaFromHttpResponse(response);
        if (area == null) {
            metricsPublisher.addCount(MetricsConstants.GETAREA_AREANOTFOUND_COUNT, 1);
            throw new AreaNotFoundException("Could not find area with id: " + uuid);
        }
        metricsPublisher.addCount(MetricsConstants.GETAREA_AREANOTFOUND_COUNT, 0);
        return area;
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

        HttpRequest request = HttpUtils.httpRequestBuilder(body);

        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("getAllAreasFromQuery client.send failed" + e.getStackTrace());
        }
        return HttpUtils.getListFromHttpResponse(response);
    }
}
