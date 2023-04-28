package ewha.gsd.midubang.domain.community.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class MessageRequestDto implements Serializable {
    private String message;
}
