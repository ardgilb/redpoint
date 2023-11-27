package com.nashss.se.redpoint.lambda;

import com.nashss.se.redpoint.activity.request.DeleteCommentRequest;
import com.nashss.se.redpoint.activity.request.DeleteLogbookEntryRequest;
import com.nashss.se.redpoint.activity.result.DeleteLogbookEntryResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
public class DeleteLogbookEntryLambda
    extends LambdaActivityRunner<DeleteLogbookEntryRequest, DeleteLogbookEntryResult>
    implements RequestHandler<AuthenticatedLambdaRequest<DeleteLogbookEntryRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteLogbookEntryRequest> input, Context context) {
        return super.runActivity(
            () -> {
                DeleteLogbookEntryRequest unauthenticatedRequest = input.fromPathAndQuery((path, query) ->
                    DeleteLogbookEntryRequest.builder()
                        .withClimbId(path.get("userId"))
                        .build());
                return input.fromUserClaims(claims ->
                    DeleteLogbookEntryRequest.builder()
                        .withUserId(claims.get("email"))
                        .withClimbId(unauthenticatedRequest.getClimbId())
                        .build());
            },
            (request, serviceComponent) ->
                serviceComponent.provideDeleteLogbookEntryActivity().handleRequest(request)
        );
    }
}



