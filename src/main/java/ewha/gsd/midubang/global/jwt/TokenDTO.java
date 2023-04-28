package ewha.gsd.midubang.global.jwt;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
    private String grantType;
    private String accessToken;
    private Long tokenExpiresIn;
    private String refreshToken;
}
