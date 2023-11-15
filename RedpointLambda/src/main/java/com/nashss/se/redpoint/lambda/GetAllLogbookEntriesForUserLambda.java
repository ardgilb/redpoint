package com.nashss.se.redpoint.lambda;

import com.nashss.se.redpoint.activity.request.GetAllLogbookEntriesForUserRequest;
import com.nashss.se.redpoint.activity.result.GetAllLogbookEntriesForUserResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetAllLogbookEntriesForUserLambda
    extends LambdaActivityRunner<GetAllLogbookEntriesForUserRequest, GetAllLogbookEntriesForUserResult>
    implements RequestHandler<LambdaRequest<GetAllLogbookEntriesForUserRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetAllLogbookEntriesForUserRequest> input, Context context) {
        return super.runActivity(
            () -> input.fromPathAndQuery((path, query) ->
                GetAllLogbookEntriesForUserRequest.builder()
                    .withUserId(path.get("userId"))
                    .build()),
            (request, serviceComponent) ->
                serviceComponent.provideGetAllLogbookEntriesForUserActivity().handleRequest(request)
        );
    }
}


