// Package declaration for Data Transfer Objects
package dto;

// Import statements for JSON handling, Language enum, and Lombok annotations
import com.fasterxml.jackson.annotation.JsonCreator; // For JSON deserialization
import com.fasterxml.jackson.annotation.JsonProperty; // For JSON property mapping
import constants.Language; // Enum for supported languages
import lombok.Getter; // Lombok annotation for getter methods
import lombok.Setter; // Lombok annotation for setter methods

// Documentation for class parameters and author
/**
 * @author krishna
 */

// Lombok annotations to generate getter and setter methods
@Getter
@Setter
// Record declaration for HelloInput with name and language fields
public record HelloInput(String name, Language language) {
  // JSON creator annotation to specify how to deserialize JSON into this record
  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  // Constructor for HelloInput with JSON property mapping
  public HelloInput(
      // Specifies that the "name" JSON property should be mapped to this parameter
      @JsonProperty("name") String name,
      // Specifies that the "language" JSON property should be mapped to this parameter
      @JsonProperty("language") Language language) {
    // Initialize the name field
    this.name = name;
    // Initialize the language field
    this.language = language;
  }
}
