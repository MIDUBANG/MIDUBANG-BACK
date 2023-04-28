package ewha.gsd.midubang.domain.word.dto;

import lombok.Getter;

@Getter
public class WordResDto {
    private SimpleWordDto simpleWordDto;
    private NaverWordDto naverWordDto;

    public WordResDto(SimpleWordDto simpleWordDto, NaverWordDto naverWordDto) {
        this.simpleWordDto = simpleWordDto;
        this.naverWordDto = naverWordDto;
    }
}
