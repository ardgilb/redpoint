package com.nashss.se.redpoint.activity;

import com.nashss.se.redpoint.activity.request.GetAllLogbookEntriesForUserRequest;
import com.nashss.se.redpoint.activity.result.GetAllLogbookEntriesForUserResult;
import com.nashss.se.redpoint.converters.ModelConverter;
import com.nashss.se.redpoint.dataaccess.LogbookEntryDao;
import com.nashss.se.redpoint.dataaccess.models.LogbookEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetAllLogbookEntriesForUsersForUserActivityTest {
    @Mock
    LogbookEntryDao logbookEntryDao;
    GetAllLogbookEntriesForUserActivity activity;

    @BeforeEach
    public void setup(){
        initMocks(this);
        activity = new GetAllLogbookEntriesForUserActivity(logbookEntryDao);
    }
    @Test
    public void handleRequest_withClimbId_returnsSortedLogbookEntrys() throws InterruptedException {
        LogbookEntry logbookEntry1 = new LogbookEntry();
        logbookEntry1.setClimbId("climb1");
        logbookEntry1.setDate(LocalDate.of(2021, 03, 20));
        logbookEntry1.setNotes("notes1");
        logbookEntry1.setUserId("user1");
        LogbookEntry logbookEntry2 = new LogbookEntry();
        logbookEntry2.setClimbId("climb2");
        logbookEntry2.setDate(LocalDate.of(2019, 03, 20));
        logbookEntry2.setUserId("user1");
        logbookEntry2.setNotes("notes2");
        LogbookEntry logbookEntry3 = new LogbookEntry();
        logbookEntry3.setClimbId("climb2");
        logbookEntry3.setDate(LocalDate.of(2023, 03, 20));
        logbookEntry3.setUserId("user1");
        logbookEntry3.setNotes("notes2");
        List<LogbookEntry> logbookEntryList = new ArrayList<>();
        logbookEntryList.add(logbookEntry1);
        logbookEntryList.add(logbookEntry2);
        logbookEntryList.add(logbookEntry3);
        when(logbookEntryDao.getAllLogbookEntries("user1")).thenReturn(logbookEntryList);

        GetAllLogbookEntriesForUserRequest request = GetAllLogbookEntriesForUserRequest.builder()
            .withUserId("user1")
            .build();

        GetAllLogbookEntriesForUserResult result = activity.handleRequest(request);

        assertEquals(result.getLogbookEntry().get(0).getDate(), ModelConverter.toLogbookEntryModel(logbookEntry3).getDate());
        assertEquals(result.getLogbookEntry().get(1).getDate(), ModelConverter.toLogbookEntryModel(logbookEntry1).getDate());
        assertEquals(result.getLogbookEntry().get(2).getDate(), ModelConverter.toLogbookEntryModel(logbookEntry2).getDate());
    }
}


