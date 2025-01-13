// Package declaration for service implementation classes
package service;

// Import statements for required classes and dependencies
import dto.EchoInput; // DTO for echo operation input
import dto.EchoOutput; // DTO for echo operation output
import dto.HelloInput; // DTO for hello operation input
import dto.HelloOutput; // DTO for hello operation output
import handler.HelloHandlerWorkflow; // Interface for hello workflow
import io.nexusrpc.handler.OperationHandler; // Handler interface for RPC operations
import io.nexusrpc.handler.OperationImpl; // Annotation for operation implementations
import io.nexusrpc.handler.ServiceImpl; // Annotation for service implementations
import io.temporal.client.WorkflowOptions; // Configuration options for Temporal workflows
import io.temporal.nexus.WorkflowClientOperationHandlers; // Utility for creating workflow handlers
import java.util.Objects; // Utility class for null checks

// Documentation for class author
/**
 * @author krishnakantverma
 */

// Marks this class as implementation of NexusDemo service
@ServiceImpl(service = NexusDemo.class)
public class NexusDemoImpl {
  // Marks this method as an operation implementation
  @OperationImpl
  // Method to create handler for echo operation
  public OperationHandler<EchoInput, EchoOutput> echo() {
    // Creates a synchronous handler for echo operation
    return WorkflowClientOperationHandlers.sync(
        // Lambda function to process the echo request
        (ctx, details, client, input) -> {
          // Check if input is not null
          if (Objects.nonNull(input)) {
            // Return new EchoOutput with the input message
            return new EchoOutput(input.message());
          }
          // Return null if input is null
          return null;
        });
  }

  // Marks this method as an operation implementation
  @OperationImpl
  // Method to create handler for hello operation
  public OperationHandler<HelloInput, HelloOutput> hello() {
    // Creates a workflow method handler
    return WorkflowClientOperationHandlers.fromWorkflowMethod(
        // Lambda function to create and configure workflow stub
        (ctx, details, client, input) ->
            // Create new workflow stub with specified options
            client.newWorkflowStub(
                    HelloHandlerWorkflow.class,
                    // Build workflow options with request ID as workflow ID
                    WorkflowOptions.newBuilder().setWorkflowId(details.getRequestId()).build())
                // Method reference to hello method
                ::hello);
  }
}
