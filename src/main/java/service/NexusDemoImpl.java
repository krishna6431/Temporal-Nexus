package service;

import dto.EchoInput;
import dto.EchoOutput;
import dto.HelloInput;
import dto.HelloOutput;
import handler.HelloHandlerWorkflow;
import io.nexusrpc.handler.OperationHandler;
import io.nexusrpc.handler.OperationImpl;
import io.nexusrpc.handler.ServiceImpl;
import io.temporal.client.WorkflowOptions;
import io.temporal.nexus.WorkflowClientOperationHandlers;

import java.util.Objects;

/**
 * @author krishnakantverma
 */

@ServiceImpl(service = NexusDemo.class)
public class NexusDemoImpl{
    @OperationImpl
    public OperationHandler<EchoInput,EchoOutput> echo() {
        // Creates a synchronous handler that processes the input and returns output
        return WorkflowClientOperationHandlers.sync(
                (ctx, details, client, input) ->
                {
                    if(Objects.nonNull(input)){
                        return new EchoOutput(input.message());
                    }
                    return null;
                });
    }

    @OperationImpl
    public OperationHandler<HelloInput, HelloOutput>hello(){
        return WorkflowClientOperationHandlers.fromWorkflowMethod(
                (ctx, details, client, input) ->
                        client.newWorkflowStub(
                                HelloHandlerWorkflow.class,
                                WorkflowOptions.newBuilder().setWorkflowId(details.getRequestId()).build())
                                ::hello);
    }
}
