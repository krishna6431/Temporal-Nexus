// Package declaration for the caller components
package caller;

// Import Temporal workflow interface annotation
import io.temporal.workflow.WorkflowInterface;
// Import Temporal workflow method annotation
import io.temporal.workflow.WorkflowMethod;

/**
 * @author krishna
 */

// Annotation to mark this interface as a Temporal workflow interface
@WorkflowInterface
// Interface definition for the Echo Caller workflow
public interface EchoCallerWorkflow {
  // Annotation to mark this method as the main workflow method
  @WorkflowMethod
  // Method declaration for echo functionality that takes a message and returns a String
  String echo(String message);
}
