package ewha.gsd.midubang.domain.community.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ChoiceDto implements Serializable {

    private String text;
    private Integer index;

    @JsonProperty("finish_reason")
    private String finishReason;

    @Builder
    public ChoiceDto (String text, Integer index, String finishReason) {
        this.text = text;
        this.index = index;
        this.finishReason = finishReason;
    }

}
