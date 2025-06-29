package org.example.xiaomiai.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OllamaResponse {
    private String model;
    private LocalDateTime created_at;
    private String response;
    private boolean done;
}
