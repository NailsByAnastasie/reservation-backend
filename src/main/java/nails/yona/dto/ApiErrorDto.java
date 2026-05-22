package nails.yona.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiErrorDto(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        Map<String, String> fieldErrors
) {}
