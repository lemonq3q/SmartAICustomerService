package org.example.xiaomiai.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OllamaRequestBody {
    private String model;
    private String prompt;
    private boolean stream;
}
