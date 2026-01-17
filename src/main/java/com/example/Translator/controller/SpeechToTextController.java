package com.example.Translator.controller;

import com.example.Translator.dto.SpeechToTextResponse;
import com.example.Translator.service.SpeechToTextService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class SpeechToTextController {

    private final SpeechToTextService speechToTextService;

    public SpeechToTextController(SpeechToTextService speechToTextService) {
        this.speechToTextService = speechToTextService;
    }

    @PostMapping(
            value = "/speech-to-text",
            consumes = "multipart/form-data"
    )
    public SpeechToTextResponse speechToText(
            @RequestParam("audio") MultipartFile audioFile) {

        // ✅ Call REAL Google Speech logic
        return speechToTextService.convert(audioFile);
    }
}
