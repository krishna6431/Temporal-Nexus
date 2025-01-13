// Defines the package name for this class
package caller;

// Import required classes and dependencies
import client.ClientOptions;
import io.temporal.client.WorkflowClient;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.WorkflowImplementationOptions;
import io.temporal.workflow.NexusServiceOptions;
import java.util.Collections;

/**
 * @author krishna
 */

// Main class that sets up and runs a Temporal worker
public class CallerWorker {
  // Constant defining the default task queue name used by this worker
  public static final String DEFAULT_TASK_QUEUE_NAME = "my-caller-workflow-task-queue";

  // Main entry point of the worker application
  public static void main(String[] args) {
    // Create a workflow client using provided command line arguments
    WorkflowClient client = ClientOptions.getWorkflowClient(args);

    // Create a new worker factory instance using the workflow client
    WorkerFactory factory = WorkerFactory.newInstance(client);

    // Create a new worker that will process workflows from the default task queue
    Worker worker = factory.newWorker(DEFAULT_TASK_QUEUE_NAME);

    // Register workflow implementation types with the worker
    worker.registerWorkflowImplementationTypes(
        // Build workflow implementation options
        WorkflowImplementationOptions.newBuilder()
            // Set Nexus service options for the worker
            .setNexusServiceOptions(
                // Create a map with single entry for NexusDemo service
                Collections.singletonMap(
                    "NexusDemo", // Service name
                    // Configure Nexus service with endpoint
                    NexusServiceOptions.newBuilder().setEndpoint("my-nexus-endpoint-name").build()))
            .build(),
        // Register the workflow implementation classes
        EchoCallerWorkflowImpl.class, // Echo workflow implementation
        HelloCallerWorkflowImpl.class); // Hello workflow implementation

    // Start the worker factory to begin processing workflows
    factory.start();
  }
}
