package com.nashss.se.redpoint.activity;

import com.nashss.se.redpoint.activity.request.UpdateLogbookEntryRequest;
import com.nashss.se.redpoint.activity.result.UpdateLogbookEntryResult;
import com.nashss.se.redpoint.converters.ModelConverter;
import com.nashss.se.redpoint.dataaccess.LogbookEntryDao;
import com.nashss.se.redpoint.dataaccess.models.LogbookEntry;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

import javax.inject.Inject;

public class UpdateLogbookEntryActivity {
    private final LogbookEntryDao logbookEntryDao;
    /**
     * Instantiates a new UpdateLogbookEntryActivity object.
     *
     * @param logbookEntryDao LogbookEntryDao to access DynamoDB.
     */
    @Inject
    UpdateLogbookEntryActivity(LogbookEntryDao logbookEntryDao) {
        this.logbookEntryDao = logbookEntryDao;
    }
    /**
     * This method handles the incoming request by updating an existing ascent with data from the request.
     * <p>
     * It then returns the ascent.
     * <p>
     *
     * @param request request object containing the data
     * @return UpdateLogbookEntryResult result object containing the {@link LogbookEntry}
     */
    public UpdateLogbookEntryResult handleRequest(UpdateLogbookEntryRequest request) {
        LogbookEntry entry = new LogbookEntry();
        entry.setUserId(request.getUserId());
        entry.setClimbId(request.getClimbId());

        LogbookEntry saved = logbookEntryDao.getLogbookEntry(entry);
        saved.setNotes(request.getNotes());
        saved.setDate(LocalDate.parse(request.getDate()));
        LogbookEntry result = logbookEntryDao.saveLogbookEntry(saved);

        return UpdateLogbookEntryResult.builder()
            .withLogbookEntry(ModelConverter.toLogbookEntryModel(result))
            .build();
    }
}


