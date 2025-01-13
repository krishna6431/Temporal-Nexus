package handler;

import dto.HelloInput;
import dto.HelloOutput;

/**
 * @author krishnakantverma
 */
public class HelloHandlerWorkflowImpl implements  HelloHandlerWorkflow{
    @Override
    public HelloOutput hello(HelloInput input) {
        return switch (input.language()) {
            case EN -> new HelloOutput("Hello " + input.name() + " 👋");
            case FR -> new HelloOutput("Bonjour " + input.name() + " 👋");
            case DE -> new HelloOutput("Hallo " + input.name() + " 👋");
            case ES -> new HelloOutput("¡Hola! " + input.name() + " 👋");
            case TR -> new HelloOutput("Merhaba " + input.name() + " 👋");
        };
    }
}
