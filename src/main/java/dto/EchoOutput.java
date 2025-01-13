package dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author krishnakantverma
 */

@Getter
@Setter
public record EchoOutput(String message) {
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public EchoOutput(@JsonProperty("message") String message) {
        this.message = message;
    }
}
