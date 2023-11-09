package com.nashss.se.redpoint.activity;

import com.nashss.se.redpoint.activity.request.CreateCommentRequest;
import com.nashss.se.redpoint.activity.result.CreateCommentResult;
import com.nashss.se.redpoint.converters.ModelConverter;
import com.nashss.se.redpoint.dataaccess.CommentDao;
import com.nashss.se.redpoint.dataaccess.models.Comment;

import java.time.ZonedDateTime;
import java.util.UUID;

import javax.inject.Inject;

public class CreateCommentActivity {
    private final CommentDao commentDao;
    /**
     * Instantiates a new CreateCommentActivity object.
     *
     * @param commentDao CommentDao to access DynamoDB.
     */
    @Inject
    CreateCommentActivity(CommentDao commentDao) {
        this.commentDao = commentDao;
    }
    /**
     * This method handles the incoming request by creating a comment with data from the request.
     * <p>
     * It then returns the comment.
     * <p>
     *
     * @param request request object containing the data
     * @return CreateCommentResult result object containing the {@link Comment}
     */
    public CreateCommentResult handleRequest(CreateCommentRequest request) {
        Comment comment = new Comment();
        comment.setCommentId(String.valueOf(UUID.randomUUID()));
        comment.setTimeStamp(ZonedDateTime.now());
        comment.setUserId(request.getUserId());
        comment.setClimbId(request.getClimbId());
        comment.setText(request.getText());

        Comment result = commentDao.saveComment(comment);
        return CreateCommentResult.builder()
            .withComment(ModelConverter.toCommentModel(result))
            .build();
    }
}
