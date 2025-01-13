package service;

import dto.EchoInput;
import dto.EchoOutput;
import dto.HelloInput;
import dto.HelloOutput;
import io.nexusrpc.Operation;
import io.nexusrpc.Service;

/**
 * @author krishna
 */
@Service
public interface NexusDemo {
    // Defines a hello operation that takes HelloInput and returns HelloOutput
    @Operation
    HelloOutput hello(HelloInput input);

    // Defines an echo operation that takes EchoInput and returns EchoOutput
    @Operation
    EchoOutput echo(EchoInput input);
}
