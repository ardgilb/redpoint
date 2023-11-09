package com.nashss.se.redpoint.dataaccess;

import com.nashss.se.redpoint.dataaccess.models.Comment;
import com.nashss.se.redpoint.metrics.MetricsPublisher;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;

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
        return this.mapper.load(comment);
    }
}
