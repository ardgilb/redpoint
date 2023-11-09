package com.nashss.se.redpoint.lambda;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.redpoint.activity.request.DeleteCommentRequest;
import com.nashss.se.redpoint.activity.result.DeleteCommentResult;

public class DeleteCommentLambda
    extends LambdaActivityRunner<DeleteCommentRequest, DeleteCommentResult>
    implements RequestHandler<AuthenticatedLambdaRequest<DeleteCommentRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteCommentRequest> input, Context context) {
        return super.runActivity(
            () -> {
                DeleteCommentRequest unauthenticatedRequest = input.fromBody(DeleteCommentRequest.class);
                return input.fromUserClaims(claims ->
                    DeleteCommentRequest.builder()
                        .withUserId(claims.get("email"))
                        .withCommentId(unauthenticatedRequest.getCommentId())
                        .build());
            },
            (request, serviceComponent) ->
                serviceComponent.provideDeleteCommentActivity().handleRequest(request)
        );
    }
}


