// Package declaration for the caller components
package caller;

// Import required classes and dependencies
import constants.Language; // Enum for supported languages
import dto.HelloInput; // DTO for hello input parameters
import dto.HelloOutput; // DTO for hello operation response
import io.temporal.workflow.NexusOperationHandle; // Handle for async Nexus operations
import io.temporal.workflow.NexusOperationOptions; // Options for Nexus operations
import io.temporal.workflow.NexusServiceOptions; // Options for Nexus service configuration
import io.temporal.workflow.Workflow; // Core Temporal workflow functionality
import java.time.Duration; // For handling time durations
import service.NexusDemo; // Nexus service interface

// Implementation class for the Hello Caller workflow interface
/**
 * @author krishna
 */
public class HelloCallerWorkflowImpl implements HelloCallerWorkflow {
  // Create a Nexus service stub with configured options
  NexusDemo nexusService =
      // Create new Nexus service stub using Temporal's Workflow class
      Workflow.newNexusServiceStub(
          // Specify the service interface class
          NexusDemo.class,
          // Configure Nexus service options
          NexusServiceOptions.newBuilder()
              // Set operation-specific options
              .setOperationOptions(
                  // Build operation options with timeout
                  NexusOperationOptions.newBuilder()
                      // Set maximum time allowed for operation completion (10 seconds)
                      .setScheduleToCloseTimeout(Duration.ofSeconds(10))
                      .build())
              .build());

  // Implementation of the hello method from HelloCallerWorkflow interface
  @Override
  public String hello(String message, Language language) {
    // Start an asynchronous Nexus operation and get its handle
    NexusOperationHandle<HelloOutput> handle =
        // Start the operation using the nexusService's hello method with input parameters
        Workflow.startNexusOperation(nexusService::hello, new HelloInput(message, language));
    // Wait for the operation execution to complete
    handle.getExecution().get();
    // Return the message from the operation result
    return handle.getResult().get().message();
  }
}
