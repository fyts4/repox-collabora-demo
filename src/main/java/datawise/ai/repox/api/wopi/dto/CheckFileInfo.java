package datawise.ai.repox.api.wopi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

@Builder
@Getter
@Validated
public class CheckFileInfo {
    @NotNull
    private String baseFileName;
    @NotNull
    private String ownerId;
    @NotNull
    private long size;
    @NotNull
    private String userId;
    private Boolean userCanEdit;
}
