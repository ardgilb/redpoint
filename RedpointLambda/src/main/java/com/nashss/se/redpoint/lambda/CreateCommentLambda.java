package com.nashss.se.redpoint.lambda;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.redpoint.activity.request.CreateCommentRequest;
import com.nashss.se.redpoint.activity.result.CreateCommentResult;

public class CreateCommentLambda
    extends LambdaActivityRunner<CreateCommentRequest, CreateCommentResult>
    implements RequestHandler<AuthenticatedLambdaRequest<CreateCommentRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateCommentRequest> input, Context context) {
        return super.runActivity(
            () -> {
                CreateCommentRequest unauthenticatedRequest = input.fromBody(CreateCommentRequest.class);
                return input.fromUserClaims(claims ->
                    CreateCommentRequest.builder()
                        .withUserId(claims.get("email"))
                        .withClimbId(unauthenticatedRequest.getClimbId())
                        .withText(unauthenticatedRequest.getText())
                        .build());
            },
            (request, serviceComponent) ->
                serviceComponent.provideCreateCommentActivity().handleRequest(request)
        );
    }
}

