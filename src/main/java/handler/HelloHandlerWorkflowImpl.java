// Package declaration for handler classes
package handler;

// Import statements for required DTOs
import dto.HelloInput; // DTO for handling input parameters (name and language)
import dto.HelloOutput; // DTO for handling output message

// Documentation for class author
/**
 * @author krishna
 */

// Class definition implementing the HelloHandlerWorkflow interface
public class HelloHandlerWorkflowImpl implements HelloHandlerWorkflow {
  // Override the hello method from the HelloHandlerWorkflow interface
  @Override
  public HelloOutput hello(HelloInput input) {
    // Switch expression based on the input language
    return switch (input.language()) {
      // For English language, return greeting with wave emoji
      case EN -> new HelloOutput("Hello " + input.name() + " 👋");
      // For French language, return greeting with wave emoji
      case FR -> new HelloOutput("Bonjour " + input.name() + " 👋");
      // For German language, return greeting with wave emoji
      case DE -> new HelloOutput("Hallo " + input.name() + " 👋");
      // For Spanish language, return greeting with wave emoji
      case ES -> new HelloOutput("¡Hola! " + input.name() + " 👋");
      // For Turkish language, return greeting with wave emoji
      case TR -> new HelloOutput("Merhaba " + input.name() + " 👋");
    };
  }
}
