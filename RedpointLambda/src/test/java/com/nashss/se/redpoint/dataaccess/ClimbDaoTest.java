package com.nashss.se.redpoint.dataaccess;

import com.amazonaws.services.cloudwatch.model.StandardUnit;
import com.nashss.se.redpoint.dataaccess.models.Climb;
import com.nashss.se.redpoint.exceptions.ClimbNotFoundException;
import com.nashss.se.redpoint.metrics.MetricsConstants;
import com.nashss.se.redpoint.metrics.MetricsPublisher;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ClimbDaoTest {
    @Mock
    HttpClient client;
    @Mock
    MetricsPublisher metricsPublisher;
    @Mock
    HttpResponse response;
    ClimbDao climbDao;
    @BeforeEach
    public void setup(){
        initMocks(this);
        climbDao = new ClimbDao(client, metricsPublisher);
    }
    @Test
    public void getClimb_validUUID_returnsClimb() throws IOException, InterruptedException {
        String uuid = "valid";
        String body = "{\"data\":{\"climb\":{\"name\":\"valid\"}}}";
        when(client.send(any(), any())).thenReturn(response);
        when(response.body()).thenReturn(body);

        Climb climb = climbDao.getClimb(uuid);

        assertNotNull(climb);
        verify(metricsPublisher).addCount(eq(MetricsConstants.GETCLIMB_CLIMBNOTFOUND_COUNT), anyDouble());


    }
    @Test
    public void getClimb_invalidUUID_throwsException() throws IOException, InterruptedException {
        String uuid = "invalid";
        String body = "{\"data\":{\"climb\":null}}";
        when(client.send(any(), any())).thenReturn(response);
        when(response.body()).thenReturn(body);

        assertThrows(ClimbNotFoundException.class, () -> climbDao.getClimb(uuid));
        verify(metricsPublisher).addCount(eq(MetricsConstants.GETCLIMB_CLIMBNOTFOUND_COUNT), anyDouble());
    }
}
