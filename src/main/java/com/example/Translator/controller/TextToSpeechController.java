package com.example.Translator.controller;

import com.example.Translator.dto.TextToSpeechRequest;
import com.example.Translator.service.TextToSpeechService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TextToSpeechController {

    private final TextToSpeechService ttsService;

    public TextToSpeechController(TextToSpeechService ttsService) {
        this.ttsService = ttsService;
    }

    @PostMapping("/text-to-speech")
    public ResponseEntity<byte[]> textToSpeech(
            @RequestBody TextToSpeechRequest request) {

        byte[] audioData =
                ttsService.synthesize(request.getText(), request.getLanguageCode());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"speech.mp3\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(audioData);
    }
}
