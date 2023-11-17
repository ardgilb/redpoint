package com.nashss.se.redpoint.activity;

import com.nashss.se.redpoint.activity.request.DeleteLogbookEntryRequest;
import com.nashss.se.redpoint.activity.result.DeleteLogbookEntryResult;
import com.nashss.se.redpoint.converters.ModelConverter;
import com.nashss.se.redpoint.dataaccess.LogbookEntryDao;

import com.nashss.se.redpoint.dataaccess.models.LogbookEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class DeleteLogbookEntryActivityTest {
    @Mock
    LogbookEntryDao logbookEntryDao;
    DeleteLogbookEntryActivity activity;

    @BeforeEach
    public void setup(){
        initMocks(this);
        activity = new DeleteLogbookEntryActivity(logbookEntryDao);
    }
    @Test
    public void handleRequest_withLogbookEntry_returnsResult(){
        LogbookEntry logbookEntry = new LogbookEntry();
        logbookEntry.setDate(LocalDate.of(2023, 11, 17));
        logbookEntry.setUserId("user");
        logbookEntry.setClimbId("climb");
        logbookEntry.setNotes("notes");
        logbookEntry.setDate(LocalDate.of(2023, 11, 17));
        when(logbookEntryDao.getLogbookEntry(any())).thenReturn(logbookEntry);

        DeleteLogbookEntryRequest request = DeleteLogbookEntryRequest.builder()
            .withClimbId("climb")
            .withUserId("user")
            .build();

        DeleteLogbookEntryResult result = activity.handleRequest(request);

        assertEquals(result.getLogbookEntry().getClimbId(), logbookEntry.getClimbId());
        assertEquals(result.getLogbookEntry().getUserId(), logbookEntry.getUserId());
        verify(logbookEntryDao).deleteLogbookEntry(any());
    }
}

