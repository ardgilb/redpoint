package com.nashss.se.redpoint.activity;

import com.nashss.se.redpoint.activity.result.GetCommentsForClimbResult;
import com.nashss.se.redpoint.activity.request.GetCommentsForClimbRequest;
import com.nashss.se.redpoint.converters.ModelConverter;
import com.nashss.se.redpoint.dataaccess.CommentDao;
import com.nashss.se.redpoint.dataaccess.models.Comment;
import com.nashss.se.redpoint.models.CommentModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Implementation of the GetCommentActivity for the MySocialNetworks's CreateComment API.
 * <p>
 * This API allows the customer to retrieve a Comment.
 */
public class GetCommentsForClimbActivity {
    private final CommentDao commentDao;

    /**
     * Instantiates a new GetCommentsForClimbActivity object.
     *
     * @param commentDao CommentDao to access the Comments table.
     */
    @Inject
    public GetCommentsForClimbActivity(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    /**
     * This method handles the incoming request by retrieving all existing Comments
     * with the provided climbId from the request.
     * <p>
     * It then returns the list of Comments sorted by date.
     *
     * @param getAllCommentsRequest request object containing the climbID associated with it
     * @return getAllCommentsResult result object containing the API defined CommentModel
     */
    public GetCommentsForClimbResult handleRequest(final GetCommentsForClimbRequest getAllCommentsRequest) {
        ModelConverter mc = new ModelConverter();
        List<Comment> commentList = commentDao.getAllComments(getAllCommentsRequest.getClimbId());
        List<CommentModel> commentModelList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentModelList.add(mc.toCommentModel(comment));
        }
        Collections.sort(commentModelList);
        return GetCommentsForClimbResult.builder()
            .withCommentList(commentModelList)
            .build();
    }
}

