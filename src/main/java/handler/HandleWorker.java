package handler;

import client.ClientOptions;
import io.temporal.client.WorkflowClient;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import service.NexusDemoImpl;

/**
 * @author krishnakantverma
 */
public class HandleWorker {
    public static final String DEFAULT_TASK_QUEUE_NAME = "my-handler-task-queue";
    public static void main(String[] args) {
        WorkflowClient client = ClientOptions.getWorkflowClient(args);
        WorkerFactory factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker(DEFAULT_TASK_QUEUE_NAME);
        worker.registerWorkflowImplementationTypes(HelloHandlerWorkflowImpl.class);
        worker.registerNexusServiceImplementation(new NexusDemoImpl());
        factory.start();
    }
}
