package caller;

import client.ClientOptions;
import constants.Language;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author krishna
 */

// Main class to start workflow executions
public class CallerStarter {
  // Logger initialization for logging workflow execution details

  private static final Logger logger = LoggerFactory.getLogger(CallerStarter.class);

  public static void main(String[] args) {
    // Create workflow client with provided configuration
    WorkflowClient client = ClientOptions.getWorkflowClient(args);

    // Configure workflow options with default task queue
    WorkflowOptions workflowOptions =
        WorkflowOptions.newBuilder().setTaskQueue(CallerWorker.DEFAULT_TASK_QUEUE_NAME).build();

    // Create and execute echo workflow
    EchoCallerWorkflow echoWorkflow =
        client.newWorkflowStub(EchoCallerWorkflow.class, workflowOptions);

    // Log echo workflow result
    logger.info("Workflow result: {}", echoWorkflow.echo("Nexus Echo ð"));

    // Log workflow execution details
    logger.info(
        "Started workflow workflowId: {} runId: {}",
        WorkflowStub.fromTyped(echoWorkflow).getExecution().getWorkflowId(),
        WorkflowStub.fromTyped(echoWorkflow).getExecution().getRunId());

    // Create and execute hello workflow
    HelloCallerWorkflow helloWorkflow =
        client.newWorkflowStub(HelloCallerWorkflow.class, workflowOptions);

    // Log hello workflow result
    logger.info("Workflow result: {}", helloWorkflow.hello("Nexus", Language.ES));
    // Log workflow execution details

    logger.info(
        "Started workflow workflowId: {} runId: {}",
        WorkflowStub.fromTyped(helloWorkflow).getExecution().getWorkflowId(),
        WorkflowStub.fromTyped(helloWorkflow).getExecution().getRunId());
  }
}
