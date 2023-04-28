package ewha.gsd.midubang.global.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class newRefreshTokenResponse {
    private String accessToken;
    private String refreshToken;
}
