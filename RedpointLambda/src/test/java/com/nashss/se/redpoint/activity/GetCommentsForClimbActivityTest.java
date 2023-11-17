package com.nashss.se.redpoint.activity;

import com.nashss.se.redpoint.activity.request.GetCommentsForClimbRequest;
import com.nashss.se.redpoint.activity.result.GetCommentsForClimbResult;
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
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetCommentsForClimbActivityTest {
    @Mock
    CommentDao commentDao;
    GetCommentsForClimbActivity activity;

    @BeforeEach
    public void setup(){
        initMocks(this);
        activity = new GetCommentsForClimbActivity(commentDao);
    }
    @Test
    public void handleRequest_withClimbId_returnsSortedComments() throws InterruptedException {
        Comment comment1 = new Comment();
        comment1.setClimbId("climb");
        comment1.setTimeStamp(ZonedDateTime.now());
        comment1.setCommentId("id1");
        comment1.setUserId("user1");
        comment1.setText("text1");
        Thread.sleep(10);
        Comment comment2 = new Comment();
        comment2.setClimbId("climb");
        comment2.setTimeStamp(ZonedDateTime.now());
        comment2.setCommentId("id2");
        comment2.setUserId("user2");
        comment2.setText("text2");
        List<Comment> commentList = new ArrayList<>();
        commentList.add(comment2);
        commentList.add(comment1);
        when(commentDao.getAllComments("climb")).thenReturn(commentList);

        GetCommentsForClimbRequest request = GetCommentsForClimbRequest.builder()
            .withClimbId("climb")
            .build();

        GetCommentsForClimbResult result = activity.handleRequest(request);

        assertEquals(result.getComment().get(0).getTimeStamp(), ModelConverter.toCommentModel(comment1).getTimeStamp());
        assertEquals(result.getComment().get(1).getTimeStamp(), ModelConverter.toCommentModel(comment2).getTimeStamp());
    }
}

