package com.nashss.se.redpoint.converters;

import com.nashss.se.redpoint.dataaccess.models.Comment;
import com.nashss.se.redpoint.dataaccess.models.LogbookEntry;
import com.nashss.se.redpoint.models.CommentModel;
import com.nashss.se.redpoint.models.LogbookEntryModel;

public class ModelConverter {

    /**
     * Converts a provided {@link Comment} into a {@link CommentModel} representation.
     *
     * @param comment the comment to convert
     * @return the converted comment
     */
    public static CommentModel toCommentModel(Comment comment) {

        return CommentModel.builder()
            .withCommentId(comment.getCommentId())
            .withTimeStamp(comment.getTimeStamp().toString())
            .withUserId(comment.getUserId())
            .withClimbId(comment.getClimbId())
            .withText(comment.getText())
            .build();
    }
    /**
     * Converts a provided {@link LogbookEntry} into a {@link LogbookEntryModel} representation.
     *
     * @param entry the entry to convert
     * @return the converted entry
     */
    public static LogbookEntryModel toLogbookEntryModel(LogbookEntry entry) {

        return LogbookEntryModel.builder()
            .withUserId(entry.getUserId())
            .withDate(entry.getDate().toString())
            .withClimbId(entry.getClimbId())
            .withNotes(entry.getNotes())
            .build();
    }
}
