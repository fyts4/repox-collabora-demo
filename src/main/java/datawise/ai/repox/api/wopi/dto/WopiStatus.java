package datawise.ai.repox.api.wopi.dto;

import lombok.Getter;

@Getter
public enum WopiStatus {
    OK(200, "Success"),
    BAD_REQUEST(400, "X-WOPI-Lock was not provided or was empty"),
    UNAUTHORIZED(401, "Invalid access token"),
    NOT_FOUND(404, "Resource not found/user unauthorized"),
    CONFLICT(409, "Lock mismatch/locked by another interface"),
    REQUEST_ENTITY_TOO_LARGE(413, "File is too large"),
    INTERNAL_SERVER_ERROR(500, "Server error"),
    NOT_IMPLEMENTED(501, "Operation not supported");

    private final int code;
    private final String message;

    WopiStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
