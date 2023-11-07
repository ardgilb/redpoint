package com.nashss.se.redpoint.activity;

import com.nashss.se.redpoint.activity.request.GetAreaRequest;
import com.nashss.se.redpoint.activity.result.GetAreaResult;
import com.nashss.se.redpoint.dataaccess.AreaDao;
import com.nashss.se.redpoint.dataaccess.models.Area;

import javax.inject.Inject;

public class GetAreaActivity {
    private final AreaDao areaDao;
    /**
     * Instantiates a new GetAreaActivity object.
     *
     * @param areaDao AreaDao to access OpenBeta API.
     */
    @Inject
    GetAreaActivity(AreaDao areaDao) {
        this.areaDao = areaDao;
    }
    /**
     * This method handles the incoming request by retrieving the area
     * with the provided uuid from the request.
     * <p>
     * It then returns the area.
     * <p>
     *
     * @param request request object containing the uuid
     * @return GetAreaResult result object containing the API defined {@link Area}
     */
    public GetAreaResult handleRequest(GetAreaRequest request) {
        Area area = areaDao.getArea(request.getUuid());
        return GetAreaResult.builder()
            .withArea(area)
            .build();
    }
}

