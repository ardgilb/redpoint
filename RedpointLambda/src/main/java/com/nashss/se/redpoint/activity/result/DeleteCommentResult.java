package com.nashss.se.redpoint.activity.result;

import com.nashss.se.redpoint.models.CommentModel;

public class DeleteCommentResult {
    private final CommentModel comment;

    private DeleteCommentResult(CommentModel comment) {
        this.comment = comment;
    }

    public CommentModel getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "DeleteCommentResult{" +
            "comment=" + comment +
            '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private CommentModel comment;

        public Builder withComment(CommentModel comment) {
            this.comment = comment;
            return this;
        }

        public DeleteCommentResult build() {
            return new DeleteCommentResult(comment);
        }
    }
}

