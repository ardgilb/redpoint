package com.nashss.se.redpoint.activity;

import com.nashss.se.redpoint.activity.request.DeleteCommentRequest;
import com.nashss.se.redpoint.activity.result.DeleteCommentResult;
import com.nashss.se.redpoint.converters.ModelConverter;
import com.nashss.se.redpoint.dataaccess.CommentDao;
import com.nashss.se.redpoint.dataaccess.models.Comment;

import javax.inject.Inject;

public class DeleteCommentActivity {
    private final CommentDao commentDao;
    /**
     * Instantiates a new DeleteCommentActivity object.
     *
     * @param commentDao CommentDao to access DynamoDB.
     */
    @Inject
    DeleteCommentActivity(CommentDao commentDao) {
        this.commentDao = commentDao;
    }
    /**
     * This method handles the incoming request by creating a comment with data from the request.
     * <p>
     * It then returns the comment.
     * <p>
     *
     * @param request request object containing the data
     * @return DeleteCommentResult result object containing the {@link Comment}
     */
    public DeleteCommentResult handleRequest(DeleteCommentRequest request) {
        Comment comment = new Comment();
        comment.setCommentId(request.getCommentId());

        Comment commentInTable = commentDao.getComment(comment);

        if (!commentInTable.getUserId().equals(request.getUserId())) {
            throw new SecurityException("Can't delete other users comments");
        }
        commentDao.deleteComment(comment);

        return DeleteCommentResult.builder()
            .withComment(ModelConverter.toCommentModel(commentInTable))
            .build();
    }
}
