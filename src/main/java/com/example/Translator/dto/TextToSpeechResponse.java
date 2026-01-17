package com.example.Translator.dto;



import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextToSpeechResponse {

    private boolean audioAvailable;
    private String audioFormat;
    private byte[] audioData;
}

