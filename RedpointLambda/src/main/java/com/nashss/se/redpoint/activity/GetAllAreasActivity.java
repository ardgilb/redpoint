package com.nashss.se.redpoint.activity;

import com.nashss.se.redpoint.activity.request.GetAllAreasRequest;
import com.nashss.se.redpoint.activity.result.GetAllAreasResult;
import com.nashss.se.redpoint.dataaccess.AreaDao;
import com.nashss.se.redpoint.dataaccess.models.Area;

import javax.inject.Inject;
import java.util.List;

public class GetAllAreasActivity {
    private final AreaDao areaDao;
    @Inject
    GetAllAreasActivity(AreaDao areaDao){
        this.areaDao = areaDao;
    }
    public GetAllAreasResult handleRequest(GetAllAreasRequest request) {
        List<Area> areas = areaDao.getAllAreasFromQuery(request.getQuery());
        return GetAllAreasResult.builder()
            .withAreaList(areas)
            .build();
    }
}
