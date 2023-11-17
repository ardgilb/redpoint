package com.nashss.se.redpoint.converter;

import com.nashss.se.redpoint.converters.ModelConverter;
import com.nashss.se.redpoint.converters.ZonedDateTimeConverter;
import com.nashss.se.redpoint.dataaccess.models.Comment;
import com.nashss.se.redpoint.dataaccess.models.LogbookEntry;
import com.nashss.se.redpoint.models.CommentModel;
import com.nashss.se.redpoint.models.LogbookEntryModel;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelConverterTest {
    ZonedDateTimeConverter converter = new ZonedDateTimeConverter();
    @Test
    public void toCommentModel_withComment_returnsMatchingModel(){
        Comment comment = new Comment();
        comment.setCommentId("id");
        comment.setClimbId("climb");
        comment.setText("text");
        comment.setUserId("user");
        comment.setTimeStamp(ZonedDateTime.now());

        CommentModel model = ModelConverter.toCommentModel(comment);

        assertEquals(comment.getCommentId(), model.getCommentId());
        assertEquals(comment.getText(), model.getText());
        assertEquals(comment.getClimbId(), model.getClimbId());
        assertEquals(comment.getTimeStamp().toString(), model.getTimeStamp());
        assertEquals(comment.getUserId(), model.getUserId());
    }
    @Test
    public void toLogbookEntryModel_withLogbookEntry_returnsMatchingModel(){
        LogbookEntry entry = new LogbookEntry();
        entry.setClimbId("climb");
        entry.setNotes("notes");
        entry.setUserId("user");
        entry.setDate(LocalDate.of(2023, 11, 17));

        LogbookEntryModel model = ModelConverter.toLogbookEntryModel(entry);

        assertEquals(entry.getNotes(), model.getNotes());
        assertEquals(entry.getClimbId(), model.getClimbId());
        assertEquals(entry.getDate().toString(), model.getDate());
        assertEquals(entry.getUserId(), model.getUserId());
    }
}
