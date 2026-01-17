package com.example.Translator.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranslationResponse {

    private String translatedText;
    private String detectedSourceLang;
    private String targetLang;
}
