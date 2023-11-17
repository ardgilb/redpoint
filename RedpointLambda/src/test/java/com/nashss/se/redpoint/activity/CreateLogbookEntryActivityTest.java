package com.nashss.se.redpoint.activity;

import com.nashss.se.redpoint.activity.request.CreateLogbookEntryRequest;
import com.nashss.se.redpoint.activity.result.CreateLogbookEntryResult;
import com.nashss.se.redpoint.converters.ModelConverter;
import com.nashss.se.redpoint.dataaccess.LogbookEntryDao;

import com.nashss.se.redpoint.dataaccess.models.LogbookEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreateLogbookEntryActivityTest {
    @Mock
    LogbookEntryDao logbookEntryDao;
    CreateLogbookEntryActivity activity;

    @BeforeEach
    public void setup(){
        initMocks(this);
        activity = new CreateLogbookEntryActivity(logbookEntryDao);
    }
    @Test
    public void handleRequest_withLogbookEntry_returnsResult(){
        LogbookEntry logbookEntry = new LogbookEntry();
        logbookEntry.setDate(LocalDate.of(2023, 11, 17));
        logbookEntry.setUserId("user");
        logbookEntry.setClimbId("climb");
        logbookEntry.setNotes("notes");
        when(logbookEntryDao.saveLogbookEntry(any())).thenReturn(logbookEntry);

        CreateLogbookEntryRequest request = CreateLogbookEntryRequest.builder()
            .withDate("2023-11-17")
            .withClimbId("climb")
            .withUserId("user")
            .withNotes("notes")
            .build();

        CreateLogbookEntryResult result = activity.handleRequest(request);

        assertEquals(result.getLogbookEntry().getUserId(), ModelConverter.toLogbookEntryModel(logbookEntry).getUserId());
        assertEquals(result.getLogbookEntry().getClimbId(), ModelConverter.toLogbookEntryModel(logbookEntry).getClimbId());
        assertEquals(result.getLogbookEntry().getNotes(), ModelConverter.toLogbookEntryModel(logbookEntry).getNotes());
        assertEquals(result.getLogbookEntry().getDate(), ModelConverter.toLogbookEntryModel(logbookEntry).getDate());
    }
}

