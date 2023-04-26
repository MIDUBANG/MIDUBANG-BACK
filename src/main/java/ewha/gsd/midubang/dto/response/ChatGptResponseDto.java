package ewha.gsd.midubang.dto.response;

import ewha.gsd.midubang.dto.ChoiceDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class ChatGptResponseDto implements Serializable {

    private String id;
    private String object;
    private LocalDate created;
    private String  model;
    private List<ChoiceDto> choices;

    @Builder
    public ChatGptResponseDto(String id, String object, LocalDate created, String model, List<ChoiceDto> choices) {
        this.id = id;
        this.object = object;
        this.created = created;
        this.model = model;
        this.choices = choices;
    }


}
