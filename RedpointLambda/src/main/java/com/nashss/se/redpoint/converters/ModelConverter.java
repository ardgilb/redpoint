package com.nashss.se.redpoint.converters;

import com.nashss.se.redpoint.dataaccess.models.Comment;
import com.nashss.se.redpoint.models.CommentModel;

public class ModelConverter {

    /**
     * Converts a provided {@link Comment} into a {@link CommentModel} representation.
     *
     * @param comment the comment to convert
     * @return the converted comment
     */
    public static CommentModel toCommentModel(Comment comment) {

        return CommentModel.builder()
            .withTimeStamp(comment.getTimeStamp().toString())
            .withUserId(comment.getUserId())
            .withClimbId(comment.getClimbId())
            .withText(comment.getText())
            .build();
    }
}
