package com.nashss.se.redpoint.activity;

import com.nashss.se.redpoint.activity.request.GetAllLogbookEntriesForUserRequest;
import com.nashss.se.redpoint.activity.result.GetAllLogbookEntriesForUserResult;
import com.nashss.se.redpoint.converters.ModelConverter;
import com.nashss.se.redpoint.dataaccess.LogbookEntryDao;
import com.nashss.se.redpoint.dataaccess.models.LogbookEntry;
import com.nashss.se.redpoint.models.LogbookEntryModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
/**
 * Implementation of the GetAllLogbookEntriesForUser for Redpoints's GetAllLogbookEntries API.
 * <p>
 * This API allows the customer to retrieve all their LogbookEntries.
 */
public class GetAllLogbookEntriesForUserActivity {
    private final LogbookEntryDao entryDao;

    /**
     * Instantiates a new GetAllLogbookEntriesForUserActivity object.
     *
     * @param entryDao LogbookEntryDao to access the LogbookEntrys table.
     */
    @Inject
    public GetAllLogbookEntriesForUserActivity(LogbookEntryDao entryDao) {
        this.entryDao = entryDao;
    }

    /**
     * This method handles the incoming request by retrieving all existing LogbookEntrys
     * with the provided climbId from the request.
     * <p>
     * It then returns the list of LogbookEntrys sorted by date.
     *
     * @param getAllLogbookEntriesRequest request object containing the climbID associated with it
     * @return GetAllLogbookEntriesResult result object containing the API defined LogbookEntryModel
     */
    public GetAllLogbookEntriesForUserResult
        handleRequest(final GetAllLogbookEntriesForUserRequest getAllLogbookEntriesRequest) {
        List<LogbookEntryModel> entryModelList = entryDao
            .getAllLogbookEntries(getAllLogbookEntriesRequest.getUserId()).stream()
            .map(entry -> ModelConverter.toLogbookEntryModel(entry))
            .sorted()
            .collect(Collectors.toList());
        return GetAllLogbookEntriesForUserResult.builder()
            .withLogbookEntryList(entryModelList)
            .build();
    }
}


