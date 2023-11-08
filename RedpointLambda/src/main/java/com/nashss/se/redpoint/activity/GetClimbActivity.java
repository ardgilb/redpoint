package com.nashss.se.redpoint.activity;

import com.nashss.se.redpoint.activity.request.GetClimbRequest;
import com.nashss.se.redpoint.activity.result.GetClimbResult;
import com.nashss.se.redpoint.dataaccess.ClimbDao;
import com.nashss.se.redpoint.dataaccess.models.Climb;

import javax.inject.Inject;

public class GetClimbActivity {
    private final ClimbDao climbDao;
    /**
     * Instantiates a new GetClimbActivity object.
     *
     * @param climbDao ClimbDao to access OpenBeta API.
     */
    @Inject
    GetClimbActivity(ClimbDao climbDao) {
        this.climbDao = climbDao;
    }
    /**
     * This method handles the incoming request by retrieving the climb
     * with the provided uuid from the request.
     * <p>
     * It then returns the climb.
     * <p>
     *
     * @param request request object containing the uuid
     * @return GetClimbResult result object containing the API defined {@link Climb}
     */
    public GetClimbResult handleRequest(GetClimbRequest request) {
        Climb climb = climbDao.getClimb(request.getUuid());
        return GetClimbResult.builder()
            .withClimb(climb)
            .build();
    }
}

