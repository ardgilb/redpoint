package com.nashss.se.redpoint.lambda;

import com.nashss.se.redpoint.activity.request.GetClimbRequest;
import com.nashss.se.redpoint.activity.result.GetClimbResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetClimbLambda
    extends LambdaActivityRunner<GetClimbRequest, GetClimbResult>
    implements RequestHandler<LambdaRequest<GetClimbRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetClimbRequest> input, Context context) {
        return super.runActivity(
            () -> input.fromPath(path ->
                GetClimbRequest.builder()
                    .withUuid(path.get("uuid"))
                    .build()),
            (request, serviceComponent) ->
                serviceComponent.provideGetClimbActivity().handleRequest(request)
        );
    }
}
