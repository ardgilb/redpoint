package com.nashss.se.redpoint.dataaccess;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.redpoint.dataaccess.models.Comment;
import com.nashss.se.redpoint.exceptions.CommentNotFoundException;
import com.nashss.se.redpoint.metrics.MetricsConstants;
import com.nashss.se.redpoint.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CommentDaoTest {
    @Mock
    DynamoDBMapper mapper;
    @Mock
    MetricsPublisher publisher;
    CommentDao commentDao;
    @BeforeEach
    public void setUp(){
        initMocks(this);
        commentDao = new CommentDao(mapper, publisher);
    }
    @Test
    public void saveComment_withComment_returnsComment(){
        Comment comment = new Comment();
        comment.setCommentId("id");

        Comment result = commentDao.saveComment(comment);

        assertEquals(comment.getCommentId(), result.getCommentId());
        verify(mapper, times(1)).save(comment);
    }
    @Test
    public void deleteComment_withComment_deletesComment(){
        Comment comment = new Comment();
        comment.setCommentId("id");

        commentDao.deleteComment(comment);

        verify(mapper, times(1)).delete(comment);
    }
    @Test
    public void getComment_validId_returnsComment(){
        Comment comment = new Comment();
        comment.setCommentId("valid");
        when(mapper.load(comment)).thenReturn(comment);

        Comment result = commentDao.getComment(comment);

        assertEquals(result, comment);
        verify(publisher, times(1)).addCount(MetricsConstants.GETCOMMENT_COMMENTNOTFOUND_COUNT, 0);
    }
    @Test
    public void getComment_invalidId_throwsException(){
        Comment comment = new Comment();
        comment.setCommentId("invalid");
        when(mapper.load(comment)).thenReturn(null);

        assertThrows(CommentNotFoundException.class, () -> commentDao.getComment(comment));
        verify(publisher, times(1)).addCount(MetricsConstants.GETCOMMENT_COMMENTNOTFOUND_COUNT, 1);
    }
}
