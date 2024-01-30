package datawise.ai.repox.api.wopi.dto;

import lombok.Getter;

public enum WopiFileInfoProperty {
    BASE_FILE_NAME("BaseFileName", true),
    DISABLE_COPY("DisableCopy", false),
    DISABLE_EXPORT("DisableExport", false),
    DISABLE_PRINT("DisablePrint", false),
    ENABLE_OWNER_TERMINATION("EnableOwnerTermination", false),
    HIDE_EXPORT_OPTION("HideExportOption", false),
    HIDE_PRINT_OPTION("HidePrintOption", false),
    HIDE_SAVE_OPTION("HideSaveOption", false),
    IS_USER_LOCKED("IsUserLocked", false),
    IS_USER_RESTRICTED("IsUserRestricted", false),
    LAST_MODIFIED_TIME("LastModifiedTime", false),
    OWNER_ID("OwnerId", true),
    POST_MESSAGE_ORIGIN("PostMessageOrigin", false),
    SIZE("Size", true),
    USER_CAN_WRITE("UserCanWrite", false),
    USER_FRIENDLY_NAME("UserFriendlyName", false),
    USER_ID("UserId", true);

    @Getter
    private final String name;

    @Getter
    private final boolean required;

    WopiFileInfoProperty(String name, boolean isRequired) {
        this.name = name;
        this.required = isRequired;
    }
}
