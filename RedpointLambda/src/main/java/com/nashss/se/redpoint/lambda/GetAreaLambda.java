package com.nashss.se.redpoint.lambda;

import com.nashss.se.redpoint.activity.request.GetAreaRequest;
import com.nashss.se.redpoint.activity.result.GetAreaResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetAreaLambda
    extends LambdaActivityRunner<GetAreaRequest, GetAreaResult>
    implements RequestHandler<LambdaRequest<GetAreaRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetAreaRequest> input, Context context) {
        return super.runActivity(
            () -> input.fromPath(path ->
                GetAreaRequest.builder()
                    .withUuid(path.get("uuid"))
                    .build()),
            (request, serviceComponent) ->
                serviceComponent.provideGetAreaActivity().handleRequest(request)
        );
    }
}
