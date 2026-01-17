package com.example.Translator.controller;

import com.example.Translator.dto.SpeechToTextResponse;
import com.example.Translator.service.SpeechToTextService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SpeechToTextController {

    private final SpeechToTextService service;

    public SpeechToTextController(SpeechToTextService service) {
        this.service = service;
    }

    @PostMapping("/speech-to-text")
    public SpeechToTextResponse speechToText(
            @RequestPart("audio") MultipartFile audio,
            @RequestParam String sourceLang) {

        return service.convert(audio, sourceLang);
    }
}
