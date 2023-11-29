package com.nashss.se.redpoint.activity;

import com.nashss.se.redpoint.activity.request.GetAllAreasRequest;
import com.nashss.se.redpoint.activity.result.GetAllAreasResult;
import com.nashss.se.redpoint.dataaccess.AreaDao;
import com.nashss.se.redpoint.dataaccess.models.Area;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class GetAllAreasActivity {
    private final AreaDao areaDao;
    /**
     * Instantiates a new GetAllAreasActivity object.
     *
     * @param areaDao AreaDao to access OpenBeta API.
     */
    @Inject
    GetAllAreasActivity(AreaDao areaDao) {
        this.areaDao = areaDao;
    }
    /**
     * This method handles the incoming request by retrieving the areas
     * matching the provided query from the request.
     * <p>
     * It then returns the list of areas matching.
     * <p>
     *
     * @param request request object containing the uuid
     * @return GetAllAreasResult result object containing a list of the API defined {@link Area}
     */
    public GetAllAreasResult handleRequest(GetAllAreasRequest request) {
        List<Area> areas = areaDao.getAllAreasFromQuery(urlDecode(request.getQuery()));
        if (areas.isEmpty()) {
            areas = new ArrayList<>();
        }
        return GetAllAreasResult.builder()
            .withAreaList(areas)
            .build();
    }
    private String urlDecode(String input) {
        try {
            return java.net.URLDecoder.decode(input, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return input;
        }
    }
}
