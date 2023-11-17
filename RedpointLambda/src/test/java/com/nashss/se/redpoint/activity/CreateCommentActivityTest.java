package com.nashss.se.redpoint.activity;

import com.nashss.se.redpoint.activity.request.CreateCommentRequest;
import com.nashss.se.redpoint.activity.result.CreateCommentResult;
import com.nashss.se.redpoint.converters.ModelConverter;
import com.nashss.se.redpoint.dataaccess.CommentDao;

import com.nashss.se.redpoint.dataaccess.models.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreateCommentActivityTest {
    @Mock
    CommentDao commentDao;
    CreateCommentActivity activity;

    @BeforeEach
    public void setup(){
        initMocks(this);
        activity = new CreateCommentActivity(commentDao);
    }
    @Test
    public void handleRequest_withComment_returnsResult(){
        Comment comment = new Comment();
        comment.setCommentId("id");
        comment.setTimeStamp(ZonedDateTime.now());
        comment.setClimbId("climb");
        comment.setUserId("user");
        comment.setText("text");
        when(commentDao.saveComment(any())).thenReturn(comment);

        CreateCommentRequest request = CreateCommentRequest.builder()
            .withText("text")
            .withClimbId("climb")
            .withUserId("user")
            .build();

        CreateCommentResult result = activity.handleRequest(request);

        assertEquals(result.getComment().getUserId(), ModelConverter.toCommentModel(comment).getUserId());
        assertEquals(result.getComment().getClimbId(), ModelConverter.toCommentModel(comment).getClimbId());
        assertEquals(result.getComment().getText(), ModelConverter.toCommentModel(comment).getText());
    }
}

