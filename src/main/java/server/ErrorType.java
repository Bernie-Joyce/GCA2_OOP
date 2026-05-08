package server;

/**
 * Enumeration of error types used throughout the application for standardized error handling.
 * Each error type maps to an HTTP status code and a descriptive error code string.
 *
 * <p>This enum provides a centralized way to define application-level errors with their
 * corresponding HTTP status codes, ensuring consistency in error responses across the API.</p>
 *
 * <h2>Error Types:</h2>
 * <ul>
 *   <li>{@link #RESOURCE_NOT_FOUND} - Indicates a requested resource does not exist (404)</li>
 *   <li>{@link #INVALID_REQUEST} - Indicates the request format or syntax is invalid (400)</li>
 *   <li>{@link #VALIDATION_ERROR} - Indicates request validation failed (400)</li>
 *   <li>{@link #INTERNAL_ERROR} - Indicates an unexpected server error occurred (500)</li>
 *   <li>{@link #UNAUTHORIZED} - Indicates authentication credentials are missing or invalid (401)</li>
 *   <li>{@link #FORBIDDEN} - Indicates the user lacks permission for the resource (403)</li>
 *   <li>{@link #CONFLICT} - Indicates a conflict with the current state of the resource (409)</li>
 *   <li>{@link #SUCCESS} - Indicates the request was processed successfully (200)</li>
 *   <li>{@link #UNSUPPORTED_OPERATION} - Indicates the operation is not supported (501)</li>
 * </ul>
 * @author Bernard Joyce
 */
public enum ErrorType {
    /**
     * Error indicating that a requested resource was not found.
     *
     * <p>HTTP Status: 404 Not Found</p>
     *
     * <p>Use this error when a client requests a resource that does not exist or has been deleted.</p>
     * @author Bernard Joyce
     */
    RESOURCE_NOT_FOUND("NOT_FOUND", 404),

    /**
     * Error indicating that the request is malformed or contains invalid syntax.
     *
     * <p>HTTP Status: 400 Bad Request</p>
     *
     * <p>Use this error when the request format is incorrect or does not conform to expected structure.</p>
     * @author Bernard Joyce
     */
    INVALID_REQUEST("INVALID_REQUEST", 400),

    /**
     * Error indicating that request validation has failed.
     *
     * <p>HTTP Status: 400 Bad Request</p>
     *
     * <p>Use this error when the request passes syntax validation but fails semantic or business logic validation.
     * For example, invalid field values or constraints not met.</p>
     * @author Bernard Joyce
     */
    VALIDATION_ERROR("VALIDATION_ERROR", 400),

    /**
     * Error indicating that an unexpected internal server error has occurred.
     *
     * <p>HTTP Status: 500 Internal Server Error</p>
     *
     * <p>Use this error for uncaught exceptions, database failures, or other unexpected server-side issues.</p>
     * @author Bernard Joyce
     */
    INTERNAL_ERROR("INTERNAL_ERROR", 500),

    /**
     * Error indicating that the request lacks valid authentication credentials.
     *
     * <p>HTTP Status: 401 Unauthorized</p>
     *
     * <p>Use this error when authentication is required but not provided, invalid, or expired.</p>
     * @author Bernard Joyce
     */
    UNAUTHORIZED("UNAUTHORIZED", 401),

    /**
     * Error indicating that the authenticated user lacks permission to access the resource.
     *
     * <p>HTTP Status: 403 Forbidden</p>
     *
     * <p>Use this error when the user is authenticated but their authorization level is insufficient
     * to perform the requested action.</p>
     * @author Bernard Joyce
     */
    FORBIDDEN("FORBIDDEN", 403),

    /**
     * Error indicating a conflict with the current state of the resource.
     *
     * <p>HTTP Status: 409 Conflict</p>
     *
     * <p>Use this error when a request conflicts with the existing state, such as attempting to create
     * a duplicate resource or modifying a resource in an incompatible state.</p>
     * @author Bernard Joyce
     */
    CONFLICT("CONFLICT", 409),

    /**
     * Indicates that the request was processed successfully.
     *
     * <p>HTTP Status: 200 OK</p>
     *
     * <p>Use this status when the operation completes without errors.</p>
     */
    SUCCESS("SUCCESS", 200),

    /**
     * Error indicating that the requested operation is not supported or implemented.
     *
     * <p>HTTP Status: 501 Not Implemented</p>
     *
     * <p>Use this error when a request targets a feature or endpoint that is not yet implemented
     * or is explicitly not supported.</p>
     * @author Bernard Joyce
     */
    UNSUPPORTED_OPERATION("UNSUPPORTED_OPERATION", 501);


    private final String code;
    private final int httpStatus;

    /**
     * Constructs an ErrorType with the specified code and HTTP status.
     * @author Bernard Joyce
     * @param code the error code string used in error responses
     * @param httpStatus the corresponding HTTP status code
     */
    ErrorType(String code, int httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    /**
     * Returns the error code string associated with this error type.
     *
     * <p>The code is a machine-readable identifier suitable for client-side error handling logic.</p>
     * @author Bernard Joyce
     * @return the error code string
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns the HTTP status code associated with this error type.
     *
     * <p>This code should be used in the HTTP response status line when returning this error.</p>
     * @author Bernard Joyce
     * @return the HTTP status code (e.g., 404, 400, 500)
     */
    public int getHttpStatus() {
        return httpStatus;
    }
}