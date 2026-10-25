package pt.simaobaltazar.dividendtracker.controller.exceptions;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import pt.simaobaltazar.dividendtracker.service.exceptions.InvalidRequestException;
import pt.simaobaltazar.dividendtracker.service.exceptions.ResourceNotFoundException;

import java.time.Instant;
import java.util.Map;

@ControllerAdvice
public class ResourceExceptionHandler {

    @GraphQlExceptionHandler
    public GraphQLError resourceNotFound(ResourceNotFoundException e, DataFetchingEnvironment env) {
        String error = "Resource not found";
        ErrorType status = ErrorType.NOT_FOUND;
        return buildError(env, status, error, e.getMessage());
    }

    @GraphQlExceptionHandler
    public GraphQLError invalidRequest(InvalidRequestException e, DataFetchingEnvironment env) {
        String error = "Invalid request";
        ErrorType status = ErrorType.BAD_REQUEST;
        return buildError(env, status, error, e.getMessage());
    }

    private GraphQLError buildError(DataFetchingEnvironment env, ErrorType status,
                                    String error, String message) {
        return GraphqlErrorBuilder.newError(env)
                .errorType(status)
                .message(message)
                .extensions(Map.of(
                        "timestamp", Instant.now().toString(),
                        "error", error
                ))
                .build();
    }
}