package com.example.myshopapp.exception;

import java.time.Instant;

public record ErrorResponse(
        String message,
        int status,
        Instant timestamp,
        String path
) {}

