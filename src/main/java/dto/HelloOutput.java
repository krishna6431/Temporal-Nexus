package dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @param message Output message field
 * @author krishnakantverma
 */
@Getter
@Setter
public record HelloOutput(String message) {
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public HelloOutput(@JsonProperty("message") String message) {
        this.message = message;
    }
}
