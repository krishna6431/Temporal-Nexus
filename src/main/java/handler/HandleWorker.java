// Package declaration for handler classes
package handler;

// Import statements for required classes
import client.ClientOptions; // Custom client configuration class
import io.temporal.client.WorkflowClient; // Temporal workflow client
import io.temporal.worker.Worker; // Temporal worker interface
import io.temporal.worker.WorkerFactory; // Factory to create Temporal workers
import service.NexusDemoImpl; // Implementation of Nexus service

// Documentation for class author
/**
 * @author krishna
 */

// Main worker class definition
public class HandleWorker {
  // Constant defining the default task queue name for the worker
  public static final String DEFAULT_TASK_QUEUE_NAME = "my-handler-task-queue";

  // Main method to start the worker
  public static void main(String[] args) {
    // Create a workflow client using provided configuration
    WorkflowClient client = ClientOptions.getWorkflowClient(args);

    // Create a new worker factory using the workflow client
    WorkerFactory factory = WorkerFactory.newInstance(client);

    // Create a new worker that polls the default task queue
    Worker worker = factory.newWorker(DEFAULT_TASK_QUEUE_NAME);

    // Register the workflow implementation class with the worker
    worker.registerWorkflowImplementationTypes(HelloHandlerWorkflowImpl.class);

    // Register the Nexus service implementation with the worker
    worker.registerNexusServiceImplementation(new NexusDemoImpl());

    // Start the worker factory to begin processing workflows
    factory.start();
  }
}
