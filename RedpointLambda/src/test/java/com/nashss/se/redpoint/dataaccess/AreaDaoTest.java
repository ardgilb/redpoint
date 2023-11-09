package com.nashss.se.redpoint.dataaccess;


import com.nashss.se.redpoint.dataaccess.models.Area;
import com.nashss.se.redpoint.exceptions.AreaNotFoundException;
import com.nashss.se.redpoint.metrics.MetricsConstants;
import com.nashss.se.redpoint.metrics.MetricsPublisher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AreaDaoTest {
    @Mock
    HttpClient client;
    @Mock
    MetricsPublisher metricsPublisher;
    @Mock
    HttpResponse response;
    AreaDao areaDao;
    @BeforeEach
    public void setup(){
        initMocks(this);
        areaDao = new AreaDao(client, metricsPublisher);
    }
    @Test
    public void getArea_validUUID_returnsArea() throws IOException, InterruptedException {
        String uuid = "valid";
        String body = "{\"data\":{\"area\":{\"areaName\":\"valid\"}}}";
        when(client.send(any(), any())).thenReturn(response);
        when(response.body()).thenReturn(body);

        Area area = areaDao.getArea(uuid);

        assertNotNull(area);
        verify(metricsPublisher).addCount(eq(MetricsConstants.GETAREA_AREANOTFOUND_COUNT), anyDouble());


    }
    @Test
    public void getArea_invalidUUID_throwsException() throws IOException, InterruptedException {
        String uuid = "invalid";
        String body = "{\"data\":{\"area\":null}}";
        when(client.send(any(), any())).thenReturn(response);
        when(response.body()).thenReturn(body);

        assertThrows(AreaNotFoundException.class, () -> areaDao.getArea(uuid));
        verify(metricsPublisher).addCount(eq(MetricsConstants.GETAREA_AREANOTFOUND_COUNT), anyDouble());
    }
    @Test
    public void getAreasFromQuery_anyQuery_returnsList() throws IOException, InterruptedException {
        String query = "any";
        String body = "{\"data\":{\"areas\":[]}}";
        when(client.send(any(), any())).thenReturn(response);
        when(response.body()).thenReturn(body);

        List<Area> areas = areaDao.getAllAreasFromQuery(query);

        assertNotNull(areas);
    }

}
