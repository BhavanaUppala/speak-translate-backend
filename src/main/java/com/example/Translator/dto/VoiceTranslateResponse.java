package com.example.Translator.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoiceTranslateResponse {

    private String originalText;
    private String detectedSourceLang;
    private String translatedText;
    private String targetLang;
    private boolean audioAvailable;
    private byte[] audioData;
}
