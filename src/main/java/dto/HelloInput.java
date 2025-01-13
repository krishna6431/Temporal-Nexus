package dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import constants.Language;
import lombok.Getter;
import lombok.Setter;

/**
 * @param name     Input parameter for name
 * @param language Input parameter for language selection
 * @author krishnakantverma
 */
@Getter
@Setter
public record HelloInput(String name, Language language) {
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public HelloInput(
            @JsonProperty("name") String name,
            @JsonProperty("language") Language language) {
        this.name = name;
        this.language = language;
    }
}
