package com.nashss.se.redpoint.activity;

import com.nashss.se.redpoint.activity.request.CreateLogbookEntryRequest;
import com.nashss.se.redpoint.activity.result.CreateLogbookEntryResult;
import com.nashss.se.redpoint.converters.ModelConverter;
import com.nashss.se.redpoint.dataaccess.LogbookEntryDao;
import com.nashss.se.redpoint.dataaccess.models.LogbookEntry;

import java.time.LocalDate;

import javax.inject.Inject;

public class CreateLogbookEntryActivity {
    private final LogbookEntryDao logbookEntryDao;
    /**
     * Instantiates a new CreateLogbookEntryActivity object.
     *
     * @param logbookEntryDao LogbookEntryDao to access DynamoDB.
     */
    @Inject
    CreateLogbookEntryActivity(LogbookEntryDao logbookEntryDao) {
        this.logbookEntryDao = logbookEntryDao;
    }
    /**
     * This method handles the incoming request by creating a ascent with data from the request.
     * <p>
     * It then returns the ascent.
     * <p>
     *
     * @param request request object containing the data
     * @return CreateLogbookEntryResult result object containing the {@link LogbookEntry}
     */
    public CreateLogbookEntryResult handleRequest(CreateLogbookEntryRequest request) {
        LogbookEntry entry = new LogbookEntry();
        entry.setUserId(request.getUserId());
        entry.setDate(LocalDate.parse(request.getDate()));
        entry.setClimbId(request.getClimbId());
        entry.setNotes(request.getNotes());

        LogbookEntry result = logbookEntryDao.saveLogbookEntry(entry);
        return CreateLogbookEntryResult.builder()
            .withLogbookEntry(ModelConverter.toLogbookEntryModel(result))
            .build();
    }
}

