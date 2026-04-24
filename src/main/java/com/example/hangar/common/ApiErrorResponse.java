package com.example.hangar.common;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record ApiErrorResponse(
    Instant timestamp,
    int statusCode,
    String reason,
    String message,
    Map<String, List<String>> errors,
    String path,
    String trace
) {
}
