package com.nashss.se.redpoint.activity;

import com.nashss.se.redpoint.activity.request.GetAllAreasRequest;
import com.nashss.se.redpoint.activity.result.GetAllAreasResult;
import com.nashss.se.redpoint.dataaccess.AreaDao;

import com.nashss.se.redpoint.dataaccess.models.Area;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetAllAreasActivityTest {
    @Mock
    AreaDao areaDao;
    GetAllAreasActivity activity;

    @BeforeEach
    public void setup(){
        initMocks(this);
        activity = new GetAllAreasActivity(areaDao);
    }
    @Test
    public void handleRequest_withQuery_returnsArea(){
        Area area = new Area();
        area.setAreaName("name");
        area.setUuid("uuid");
        List<Area> areaList = new ArrayList<>();
        areaList.add(area);
        when(areaDao.getAllAreasFromQuery("query")).thenReturn(areaList);

        GetAllAreasRequest request = GetAllAreasRequest.builder()
            .withQuery("query")
            .build();

        GetAllAreasResult result = activity.handleRequest(request);

        assertEquals(result.getArea(), areaList);
    }
}
