package com.example.Translator.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpeechToTextResponse {

    private String text;
    private String detectedLanguage;
    private Float confidence;
}


