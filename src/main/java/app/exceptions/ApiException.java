package app.exceptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ApiException extends RuntimeException {

    private final int statusCode;
    private final String timestamp;

    public ApiException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getTimestamp() {
        return timestamp;
    }

}