package handler;

import dto.HelloInput;
import dto.HelloOutput;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

/**
 * @author krishnakantverma
 */
@WorkflowInterface
public interface HelloHandlerWorkflow {
    @WorkflowMethod
    HelloOutput hello(HelloInput input);
}