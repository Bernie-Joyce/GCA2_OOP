package server;

public enum ErrorType {
    RESOURCE_NOT_FOUND("NOT_FOUND", 404),
    INVALID_REQUEST("INVALID_REQUEST", 400),
    VALIDATION_ERROR("VALIDATION_ERROR", 400),
    INTERNAL_ERROR("INTERNAL_ERROR", 500),
    UNAUTHORIZED("UNAUTHORIZED", 401),
    FORBIDDEN("FORBIDDEN", 403),
    CONFLICT("CONFLICT", 409),
    UNSUPPORTED_OPERATION("UNSUPPORTED_OPERATION", 501);

    private final String code;
    private final int httpStatus;

    ErrorType(String code, int httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}