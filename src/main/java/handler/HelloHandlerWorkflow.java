// Package declaration for handler classes
package handler;

// Import statements for required classes
import dto.HelloInput; // DTO for hello operation input
import dto.HelloOutput; // DTO for hello operation output
import io.temporal.workflow.WorkflowInterface; // Temporal annotation for workflow interfaces
import io.temporal.workflow.WorkflowMethod; // Temporal annotation for workflow methods

// Documentation for class author
/**
 * @author krishna
 */

// Marks this interface as a Temporal workflow interface
@WorkflowInterface
// Interface definition for the hello handler workflow
public interface HelloHandlerWorkflow {
  // Marks this method as the main workflow method
  @WorkflowMethod
  // Method declaration that takes HelloInput and returns HelloOutput
  HelloOutput hello(HelloInput input);
}
