package com.nashss.se.redpoint.activity;

import com.nashss.se.redpoint.activity.request.GetAreaRequest;
import com.nashss.se.redpoint.activity.result.GetAreaResult;
import com.nashss.se.redpoint.dataaccess.AreaDao;
import com.nashss.se.redpoint.dataaccess.models.Area;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetAreaActivityTest {
    @Mock
    AreaDao areaDao;
    GetAreaActivity activity;

    @BeforeEach
    public void setup(){
        initMocks(this);
        activity = new GetAreaActivity(areaDao);
    }
    @Test
    public void handleRequest_withQuery_returnsArea(){
        Area area = new Area();
        area.setAreaName("name");
        area.setUuid("uuid");
        when(areaDao.getArea("uuid")).thenReturn(area);

        GetAreaRequest request = GetAreaRequest.builder()
            .withUuid("uuid")
            .build();

        GetAreaResult result = activity.handleRequest(request);

        assertEquals(result.getArea(), area);
    }
}
