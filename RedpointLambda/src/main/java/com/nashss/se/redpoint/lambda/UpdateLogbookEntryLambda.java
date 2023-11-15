package com.nashss.se.redpoint.lambda;

import com.nashss.se.redpoint.activity.request.UpdateLogbookEntryRequest;
import com.nashss.se.redpoint.activity.result.UpdateLogbookEntryResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class UpdateLogbookEntryLambda
    extends LambdaActivityRunner<UpdateLogbookEntryRequest, UpdateLogbookEntryResult>
    implements RequestHandler<AuthenticatedLambdaRequest<UpdateLogbookEntryRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateLogbookEntryRequest> input, Context context) {
        return super.runActivity(
            () -> {
                UpdateLogbookEntryRequest unauthenticatedRequest = input.fromBody(UpdateLogbookEntryRequest.class);
                return input.fromUserClaims(claims ->
                    UpdateLogbookEntryRequest.builder()
                        .withUserId(claims.get("email"))
                        .withDate(unauthenticatedRequest.getDate())
                        .withClimbId(unauthenticatedRequest.getClimbId())
                        .withNotes(unauthenticatedRequest.getNotes())
                        .build());
            },
            (request, serviceComponent) ->
                serviceComponent.provideUpdateLogbookEntryActivity().handleRequest(request)
        );
    }
}



