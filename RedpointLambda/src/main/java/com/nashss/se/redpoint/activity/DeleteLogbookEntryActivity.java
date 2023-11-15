package com.nashss.se.redpoint.activity;

import com.nashss.se.redpoint.activity.request.DeleteLogbookEntryRequest;
import com.nashss.se.redpoint.activity.result.DeleteLogbookEntryResult;
import com.nashss.se.redpoint.converters.ModelConverter;
import com.nashss.se.redpoint.dataaccess.LogbookEntryDao;
import com.nashss.se.redpoint.dataaccess.models.LogbookEntry;

import javax.inject.Inject;

public class DeleteLogbookEntryActivity {
    private final LogbookEntryDao entryDao;
    /**
     * Instantiates a new DeleteLogbookEntryActivity object.
     *
     * @param entryDao LogbookEntryDao to access DynamoDB.
     */
    @Inject
    DeleteLogbookEntryActivity(LogbookEntryDao entryDao) {
        this.entryDao = entryDao;
    }
    /**
     * This method handles the incoming request by deleting the entry matching data from the request.
     * <p>
     * It then returns the entry.
     * <p>
     *
     * @param request request object containing the data
     * @return DeleteLogbookEntryResult result object containing the {@link LogbookEntry}
     */
    public DeleteLogbookEntryResult handleRequest(DeleteLogbookEntryRequest request) {
        LogbookEntry entry = new LogbookEntry();
        entry.setUserId(request.getUserId());
        entry.setClimbId(request.getClimbId());

        LogbookEntry entryInTable = entryDao.getLogbookEntry(entry);

        if (!entryInTable.getUserId().equals(request.getUserId())) {
            throw new SecurityException("Can't delete other users entries");
        }

        entryDao.deleteLogbookEntry(entry);

        return DeleteLogbookEntryResult.builder()
            .withLogbookEntry(ModelConverter.toLogbookEntryModel(entryInTable))
            .build();
    }
}

