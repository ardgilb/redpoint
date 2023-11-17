package com.nashss.se.redpoint.dataaccess;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nashss.se.redpoint.dataaccess.models.Comment;
import com.nashss.se.redpoint.exceptions.CommentNotFoundException;
import com.nashss.se.redpoint.metrics.MetricsConstants;
import com.nashss.se.redpoint.metrics.MetricsPublisher;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentDao {
    DynamoDBMapper mapper;
    MetricsPublisher metricsPublisher;
    /**
     * Instantiates a CommentDao object.
     *
     * @param mapper the {@link DynamoDBMapper} used to interact with the DDB tables
     * @param metricsPublisher the {@link MetricsPublisher} used to publish metrics to CloudWatch
     */
    @Inject
    CommentDao(DynamoDBMapper mapper, MetricsPublisher metricsPublisher) {
        this.mapper = mapper;
        this.metricsPublisher = metricsPublisher;
    }
    /**
     * Saves (creates or updates) the given comment.
     *
     * @param comment The comment to save
     * @return The Comment object that was saved
     */
    public Comment saveComment(Comment comment) {
        this.mapper.save(comment);
        return comment;
    }
    /**
     * Deletes the given comment.
     *
     * @param comment The comment to delete
     */
    public void deleteComment(Comment comment) {
        this.mapper.delete(comment);
    }
    /**
     * Gets the given comment.
     *
     * @param comment The comment to get
     * @return The Comment object that was retrieved.
     */
    public Comment getComment(Comment comment) {
        Comment result = this.mapper.load(comment);
        if (result == null) {
            metricsPublisher.addCount(MetricsConstants.GETCOMMENT_COMMENTNOTFOUND_COUNT, 1);
            throw new CommentNotFoundException("Comment not found for provided commentId");
        }
        metricsPublisher.addCount(MetricsConstants.GETCOMMENT_COMMENTNOTFOUND_COUNT, 0);
        return result;
    }
    /**
     * Gets all comments matching the given climbId.
     *
     * @param climbId The climbId to get all comments for
     * @return The list of Comment objects that match.
     */
    public List<Comment> getAllComments(String climbId) {
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":climbId", new AttributeValue().withS(climbId));
        DynamoDBQueryExpression<Comment> queryExpression = new DynamoDBQueryExpression<Comment>()
            .withIndexName("ClimbIdIndex")
            .withConsistentRead(false)
            .withKeyConditionExpression("climbId = :climbId")
            .withExpressionAttributeValues(valueMap);

        return mapper.query(Comment.class, queryExpression);
    }
}
