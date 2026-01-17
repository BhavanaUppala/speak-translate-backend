package com.example.Translator.service;

import com.example.Translator.dto.TranslationRequest;
import com.example.Translator.dto.TranslationResponse;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import org.springframework.stereotype.Service;


@Service
public class TranslationService {

    private final Translate translate =
            TranslateOptions.getDefaultInstance().getService();

    public TranslationResponse translateText(TranslationRequest request) {

        Translation translation;

        if ("auto".equals(request.getSourceLang())) {
            translation = translate.translate(
                    request.getText(),
                    Translate.TranslateOption.targetLanguage(request.getTargetLang())
            );
        } else {
            translation = translate.translate(
                    request.getText(),
                    Translate.TranslateOption.sourceLanguage(request.getSourceLang()),
                    Translate.TranslateOption.targetLanguage(request.getTargetLang())
            );
        }

        return new TranslationResponse(
                translation.getTranslatedText(),
                request.getSourceLang(),
                request.getTargetLang()
        );
    }
}

