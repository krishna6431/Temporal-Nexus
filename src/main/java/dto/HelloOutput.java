// Package declaration for Data Transfer Objects
package dto;

// Import statements for JSON handling and Lombok annotations
import com.fasterxml.jackson.annotation.JsonCreator; // For JSON deserialization
import com.fasterxml.jackson.annotation.JsonProperty; // For JSON property mapping
import lombok.Getter; // Lombok annotation for getter methods
import lombok.Setter; // Lombok annotation for setter methods

// Documentation for message parameter and author
/**
 * @author krishna
 */

// Lombok annotations to generate getter and setter methods
@Getter
@Setter
// Record declaration for HelloOutput with a message field
public record HelloOutput(String message) {
  // JSON creator annotation to specify how to deserialize JSON into this record
  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  // Constructor for HelloOutput with JSON property mapping
  public HelloOutput(
      // Specifies that the "message" JSON property should be mapped to this parameter
      @JsonProperty("message") String message) {
    // Call to the canonical constructor of the record
    this.message = message;
  }
}
