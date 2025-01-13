// Package declaration for the caller components
package caller;

// Import for Language enumeration used to specify greeting language
import constants.Language;
// Import for Temporal workflow interface annotation
import io.temporal.workflow.WorkflowInterface;
// Import for Temporal workflow method annotation
import io.temporal.workflow.WorkflowMethod;

/**
 * @author krishna
 */

// Annotation marking this as a Temporal workflow interface
@WorkflowInterface
// Interface definition for the Hello Caller workflow
public interface HelloCallerWorkflow {
  // Annotation marking this as the main workflow method
  @WorkflowMethod
  // Method declaration that takes a message and language parameter and returns a String
  String hello(String message, Language language);
}
