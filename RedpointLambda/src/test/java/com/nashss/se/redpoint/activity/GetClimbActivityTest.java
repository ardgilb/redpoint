package com.nashss.se.redpoint.activity;

import com.nashss.se.redpoint.activity.request.GetClimbRequest;
import com.nashss.se.redpoint.activity.result.GetClimbResult;
import com.nashss.se.redpoint.dataaccess.ClimbDao;
import com.nashss.se.redpoint.dataaccess.models.Climb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetClimbActivityTest {
    @Mock
    ClimbDao climbDao;
    GetClimbActivity activity;

    @BeforeEach
    public void setup(){
        initMocks(this);
        activity = new GetClimbActivity(climbDao);
    }
    @Test
    public void handleRequest_withQuery_returnsClimb(){
        Climb climb = new Climb();
        climb.setName("name");
        climb.setUuid("uuid");
        when(climbDao.getClimb("uuid")).thenReturn(climb);

        GetClimbRequest request = GetClimbRequest.builder()
            .withUuid("uuid")
            .build();

        GetClimbResult result = activity.handleRequest(request);

        assertEquals(result.getClimb(), climb);
    }
}

