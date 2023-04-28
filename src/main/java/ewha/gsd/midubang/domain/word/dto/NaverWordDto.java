package ewha.gsd.midubang.domain.word.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NaverWordDto {
    private String title;
    private String link;
    private String description;

    @Builder
    public NaverWordDto(JsonNode node, String fullDescription) {
        this.title = node.get("title").asText().replaceAll("\\<.*?\\>", "");
        this.link = node.get("link").asText();
        this.description = fullDescription;
    }
}
