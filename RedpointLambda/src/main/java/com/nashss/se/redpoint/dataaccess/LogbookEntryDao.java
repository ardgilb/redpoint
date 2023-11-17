package com.nashss.se.redpoint.dataaccess;

import com.nashss.se.redpoint.dataaccess.models.LogbookEntry;
import com.nashss.se.redpoint.exceptions.LogbookEntryNotFoundException;
import com.nashss.se.redpoint.metrics.MetricsConstants;
import com.nashss.se.redpoint.metrics.MetricsPublisher;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class LogbookEntryDao {
    DynamoDBMapper mapper;
    MetricsPublisher metricsPublisher;
    /**
     * Instantiates a LogbookEntryDao object.
     *
     * @param mapper the {@link DynamoDBMapper} used to interact with the DDB tables
     * @param metricsPublisher the {@link MetricsPublisher} used to publish metrics to CloudWatch
     */
    @Inject
    LogbookEntryDao(DynamoDBMapper mapper, MetricsPublisher metricsPublisher) {
        this.mapper = mapper;
        this.metricsPublisher = metricsPublisher;
    }
    /**
     * Saves (creates or updates) the given entry.
     *
     * @param entry The entry to save
     * @return The LogbookEntry object that was saved
     */
    public LogbookEntry saveLogbookEntry(LogbookEntry entry) {
        this.mapper.save(entry);
        return entry;
    }
    /**
     * Deletes the given entry.
     *
     * @param entry The entry to delete
     */
    public void deleteLogbookEntry(LogbookEntry entry) {
        this.mapper.delete(entry);
    }
    /**
     * Gets the given entry.
     *
     * @param entry The entry to get
     * @return The LogbookEntry object that was retrieved.
     */
    public LogbookEntry getLogbookEntry(LogbookEntry entry) {
        LogbookEntry result = this.mapper.load(entry);
        if (result == null) {
            metricsPublisher.addCount(MetricsConstants.GETENTRY_ENTRYNOTFOUND_COUNT, 1);
            throw new LogbookEntryNotFoundException("No Logbook Entry found matching parameters");
        }
        metricsPublisher.addCount(MetricsConstants.GETENTRY_ENTRYNOTFOUND_COUNT, 0);

        return result;
    }
    /**
     * Gets all entries matching the given userId.
     *
     * @param userId The userId to get all entrys for
     * @return The list of LogbookEntry objects that match.
     */
    public List<LogbookEntry> getAllLogbookEntries(String userId) {
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":userId", new AttributeValue().withS(userId));
        DynamoDBQueryExpression<LogbookEntry> queryExpression = new DynamoDBQueryExpression<LogbookEntry>()
            .withKeyConditionExpression("userId = :userId")
            .withExpressionAttributeValues(valueMap);

        return mapper.query(LogbookEntry.class, queryExpression);
    }
}
