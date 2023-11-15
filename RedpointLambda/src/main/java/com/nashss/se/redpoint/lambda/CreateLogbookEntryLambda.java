package com.nashss.se.redpoint.lambda;

import com.nashss.se.redpoint.activity.request.CreateLogbookEntryRequest;
import com.nashss.se.redpoint.activity.result.CreateLogbookEntryResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class CreateLogbookEntryLambda
    extends LambdaActivityRunner<CreateLogbookEntryRequest, CreateLogbookEntryResult>
    implements RequestHandler<AuthenticatedLambdaRequest<CreateLogbookEntryRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateLogbookEntryRequest> input, Context context) {
        return super.runActivity(
            () -> {
                CreateLogbookEntryRequest unauthenticatedRequest = input.fromBody(CreateLogbookEntryRequest.class);
                return input.fromUserClaims(claims ->
                    CreateLogbookEntryRequest.builder()
                        .withUserId(claims.get("email"))
                        .withClimbId(unauthenticatedRequest.getClimbId())
                        .withDate(unauthenticatedRequest.getDate())
                        .withNotes(unauthenticatedRequest.getNotes())
                        .build());
            },
            (request, serviceComponent) ->
                serviceComponent.provideCreateLogbookEntryActivity().handleRequest(request)
        );
    }
}


