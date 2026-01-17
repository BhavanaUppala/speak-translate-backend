package com.example.Translator.service;

import com.example.Translator.dto.SpeechToTextResponse;
import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SpeechToTextService {

    public SpeechToTextResponse convert(MultipartFile audio, String sourceLang) {
        try (SpeechClient speechClient = SpeechClient.create()) {

            ByteString audioBytes = ByteString.copyFrom(audio.getBytes());

            RecognitionConfig.Builder configBuilder =
                    RecognitionConfig.newBuilder()
                            .setEncoding(RecognitionConfig.AudioEncoding.WEBM_OPUS)
                            .setSampleRateHertz(48000);

            // 🔥 AUTO-DETECT OR MANUAL
            if (!"auto".equals(sourceLang)) {
                configBuilder.setLanguageCode(sourceLang);
            }

            RecognitionConfig config = configBuilder.build();

            RecognitionAudio recognitionAudio =
                    RecognitionAudio.newBuilder()
                            .setContent(audioBytes)
                            .build();

            RecognizeResponse response =
                    speechClient.recognize(config, recognitionAudio);

            String recognizedText = "";

            if (!response.getResultsList().isEmpty()) {
                SpeechRecognitionResult result = response.getResultsList().get(0);
                if (!result.getAlternativesList().isEmpty()) {
                    recognizedText = result.getAlternativesList().get(0).getTranscript();
                }
            }

            return new SpeechToTextResponse(recognizedText);

        } catch (Exception e) {
            e.printStackTrace();
            return new SpeechToTextResponse("");
        }
    }
}
