package com.nashss.se.redpoint.activity.result;

import com.nashss.se.redpoint.models.CommentModel;

import java.util.List;

public class GetCommentsForClimbResult {
    private final List<CommentModel> commentList;

    private GetCommentsForClimbResult(List<CommentModel> commentList) {
        this.commentList = commentList;
    }

    public List<CommentModel> getComment() {
        return this.commentList;
    }

    @Override
    public String toString() {
        return "GetCommentsForClimbResult{" +
            "commentList=" + commentList +
            '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<CommentModel> commentList;

        public Builder withCommentList(List<CommentModel> commentList) {
            this.commentList = commentList;
            return this;
        }

        public GetCommentsForClimbResult build() {
            return new GetCommentsForClimbResult(commentList);
        }
    }
}

