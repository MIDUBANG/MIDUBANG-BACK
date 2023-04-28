package ewha.gsd.midubang.domain.word.dto;

import lombok.Getter;

@Getter
public class MyWordResDto {
    private WordDto wordDto;
    private NaverWordDto naverWordDto;

    public MyWordResDto(WordDto wordDto, NaverWordDto naverWordDto) {
        this.wordDto = wordDto;
        this.naverWordDto = naverWordDto;
    }
}
