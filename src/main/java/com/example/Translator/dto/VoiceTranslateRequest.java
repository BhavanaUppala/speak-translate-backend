package com.example.Translator.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoiceTranslateRequest {

    private String targetLang;
    private boolean enableAudio;
}
