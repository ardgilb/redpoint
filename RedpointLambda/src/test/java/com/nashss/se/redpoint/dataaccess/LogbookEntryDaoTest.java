package com.nashss.se.redpoint.dataaccess;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.redpoint.dataaccess.models.LogbookEntry;
import com.nashss.se.redpoint.exceptions.LogbookEntryNotFoundException;
import com.nashss.se.redpoint.metrics.MetricsConstants;
import com.nashss.se.redpoint.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class LogbookEntryDaoTest {
    @Mock
    DynamoDBMapper mapper;
    @Mock
    MetricsPublisher publisher;
    LogbookEntryDao logbookEntryDao;
    @BeforeEach
    public void setUp(){
        initMocks(this);
        logbookEntryDao = new LogbookEntryDao(mapper, publisher);
    }
    @Test
    public void saveLogbookEntry_withLogbookEntry_returnsLogbookEntry(){
        LogbookEntry logbookEntry = new LogbookEntry();
        logbookEntry.setUserId("user");
        logbookEntry.setClimbId("climb");

        LogbookEntry result = logbookEntryDao.saveLogbookEntry(logbookEntry);

        assertEquals(logbookEntry.getClimbId(), result.getClimbId());
        assertEquals(logbookEntry.getUserId(), result.getUserId());
        verify(mapper, times(1)).save(logbookEntry);
    }
    @Test
    public void deleteLogbookEntry_withLogbookEntry_deletesLogbookEntry(){
        LogbookEntry logbookEntry = new LogbookEntry();
        logbookEntry.setUserId("user");
        logbookEntry.setClimbId("climb");

        logbookEntryDao.deleteLogbookEntry(logbookEntry);

        verify(mapper, times(1)).delete(logbookEntry);
    }
    @Test
    public void getLogbookEntry_validId_returnsLogbookEntry(){
        LogbookEntry logbookEntry = new LogbookEntry();
        logbookEntry.setUserId("user");
        logbookEntry.setClimbId("climb");
        when(mapper.load(logbookEntry)).thenReturn(logbookEntry);

        LogbookEntry result = logbookEntryDao.getLogbookEntry(logbookEntry);

        assertEquals(result, logbookEntry);
        verify(publisher, times(1)).addCount(MetricsConstants.GETENTRY_ENTRYNOTFOUND_COUNT, 0);
    }
    @Test
    public void getLogbookEntry_invalidId_throwsException(){
        LogbookEntry logbookEntry = new LogbookEntry();
        logbookEntry.setUserId("user");
        logbookEntry.setClimbId("climb");
        when(mapper.load(logbookEntry)).thenReturn(null);

        assertThrows(LogbookEntryNotFoundException.class, () -> logbookEntryDao.getLogbookEntry(logbookEntry));
        verify(publisher, times(1)).addCount(MetricsConstants.GETENTRY_ENTRYNOTFOUND_COUNT, 1);
    }
}

