package com.nashss.se.redpoint.lambda;

import com.nashss.se.redpoint.activity.request.GetAllAreasRequest;
import com.nashss.se.redpoint.activity.result.GetAllAreasResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class GetAllAreasLambda
    extends LambdaActivityRunner<GetAllAreasRequest, GetAllAreasResult>
    implements RequestHandler<LambdaRequest<GetAllAreasRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetAllAreasRequest> input, Context context) {
        return super.runActivity(
            () -> input.fromPath(path ->
                GetAllAreasRequest.builder()
                    .withQuery(path.get("query"))
                    .build()),
            (request, serviceComponent) ->
                serviceComponent.provideGetAllAreasActivity().handleRequest(request)
        );
    }
}
