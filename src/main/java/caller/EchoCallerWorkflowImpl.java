// Package declaration for the caller components
package caller;

// Import required classes
import dto.EchoInput; // Data Transfer Object for echo input
import io.temporal.workflow.NexusOperationOptions; // Options for Nexus operations
import io.temporal.workflow.NexusServiceOptions; // Options for Nexus service configuration
import io.temporal.workflow.Workflow; // Core Temporal workflow functionality
import java.time.Duration; // For handling time durations
import service.NexusDemo; // Nexus service interface

/**
 * @author krishna
 */

// Implementation class for the Echo Caller workflow interface
public class EchoCallerWorkflowImpl implements EchoCallerWorkflow {
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
                      // Set maximum time allowed for operation completion (1000 seconds)
                      .setScheduleToCloseTimeout(Duration.ofSeconds(1000))
                      .build())
              .build());

  // Implementation of the echo method from EchoCallerWorkflow interface
  @Override
  public String echo(String message) {
    // Call the echo method on nexusService, wrap the message in EchoInput, and return the response
    // message
    return nexusService.echo(new EchoInput(message)).message();
  }
}
