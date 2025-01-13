// Package declaration for service classes
package service;

// Import statements for required DTOs and Nexus annotations
import dto.EchoInput; // DTO for echo operation input
import dto.EchoOutput; // DTO for echo operation output
import dto.HelloInput; // DTO for hello operation input
import dto.HelloOutput; // DTO for hello operation output
import io.nexusrpc.Operation; // Nexus annotation for marking RPC operations
import io.nexusrpc.Service; // Nexus annotation for marking service interfaces

// Documentation for class author
/**
 * @author krishna
 */

// Marks this interface as a Nexus RPC service
@Service
// Interface definition for the Nexus demo service
public interface NexusDemo {
  // Marks this method as a Nexus RPC operation
  @Operation
  // Method declaration for hello operation with input/output DTOs
  HelloOutput hello(HelloInput input);

  // Marks this method as a Nexus RPC operation
  @Operation
  // Method declaration for echo operation with input/output DTOs
  EchoOutput echo(EchoInput input);
}
