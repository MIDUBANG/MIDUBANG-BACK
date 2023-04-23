package ewha.gsd.midubang.dto.response;

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
