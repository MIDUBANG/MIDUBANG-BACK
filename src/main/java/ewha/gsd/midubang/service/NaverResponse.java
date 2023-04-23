package ewha.gsd.midubang.service;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class NaverResponse {
    private LocalDateTime lastBuildDate;
    private Long total;
    private Long start;
    private Long display;
    private List<NaverDictSummary> items;

}
