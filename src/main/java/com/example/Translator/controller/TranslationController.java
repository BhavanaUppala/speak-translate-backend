package com.example.Translator.controller;


import com.example.Translator.dto.TranslationRequest;
import com.example.Translator.dto.TranslationResponse;
import com.example.Translator.service.TranslationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TranslationController {

    private final TranslationService translationService;

    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @PostMapping("/translate")
    public TranslationResponse translate(@RequestBody TranslationRequest request) {
        return translationService.translateText(request);
    }
}
