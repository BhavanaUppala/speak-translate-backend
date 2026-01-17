package com.example.Translator.controller;

import com.example.Translator.dto.VoiceTranslateResponse;
import com.example.Translator.service.SpeechToTextService;
import com.example.Translator.service.TranslationService;
import com.example.Translator.service.TextToSpeechService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class VoiceTranslateController {

    private final SpeechToTextService speechService;
    private final TranslationService translationService;
    private final TextToSpeechService ttsService;

    public VoiceTranslateController(
            SpeechToTextService speechService,
            TranslationService translationService,
            TextToSpeechService ttsService) {

        this.speechService = speechService;
        this.translationService = translationService;
        this.ttsService = ttsService;
    }

    @PostMapping(value = "/voice-translate", consumes = "multipart/form-data")
    public ResponseEntity<byte[]> voiceTranslate(
            @RequestParam("audio") MultipartFile audio,
            @RequestParam("targetLang") String targetLang,
            @RequestParam("ttsLang") String ttsLang) {

        // 1️⃣ Speech → Text
        var speechResult = speechService.convert(audio);

        // 2️⃣ Text → Translate
        var translationResult =
                translationService.translateText(
                        new com.example.Translator.dto.TranslationRequest(
                                speechResult.getText(), targetLang));

        // 3️⃣ Text → Speech
        byte[] audioOutput =
                ttsService.synthesize(
                        translationResult.getTranslatedText(), ttsLang);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"translated.mp3\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(audioOutput);
    }
}
