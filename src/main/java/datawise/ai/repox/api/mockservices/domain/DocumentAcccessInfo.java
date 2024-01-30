package datawise.ai.repox.api.mockservices.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class DocumentAcccessInfo {
    private String uuid;
    private String filename;
    private String url;
    private Long size;
}
