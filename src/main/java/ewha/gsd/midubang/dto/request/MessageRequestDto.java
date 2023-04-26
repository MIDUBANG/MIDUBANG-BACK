package ewha.gsd.midubang.dto.request;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class MessageRequestDto implements Serializable {
    private String message;
}
