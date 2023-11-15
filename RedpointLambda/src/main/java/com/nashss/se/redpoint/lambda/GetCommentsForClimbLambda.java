package com.nashss.se.redpoint.lambda;

import com.nashss.se.redpoint.activity.request.GetCommentsForClimbRequest;
import com.nashss.se.redpoint.activity.result.GetCommentsForClimbResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetCommentsForClimbLambda
    extends LambdaActivityRunner<GetCommentsForClimbRequest, GetCommentsForClimbResult>
    implements RequestHandler<LambdaRequest<GetCommentsForClimbRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetCommentsForClimbRequest> input, Context context) {
        return super.runActivity(
            () -> input.fromPathAndQuery((path, query) ->
                GetCommentsForClimbRequest.builder()
                    .withClimbId(path.get("climbId"))
                    .build()),
            (request, serviceComponent) ->
                serviceComponent.provideGetCommentsForClimbActivity().handleRequest(request)
        );
    }
}


