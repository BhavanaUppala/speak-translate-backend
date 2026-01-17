package com.example.Translator.service;

import com.example.Translator.dto.SpeechToTextResponse;
import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class SpeechToTextService {

    public SpeechToTextResponse convert(MultipartFile audioFile) {

        try (SpeechClient speechClient = SpeechClient.create()) {

            ByteString audioBytes =
                    ByteString.copyFrom(audioFile.getBytes());

            RecognitionConfig config = RecognitionConfig.newBuilder()
                    .setEncoding(RecognitionConfig.AudioEncoding.WEBM_OPUS)
                    .setEnableAutomaticPunctuation(true)
                    .setLanguageCode("en-IN")
                    .addAlternativeLanguageCodes("hi-IN")
                    .addAlternativeLanguageCodes("te-IN")
                    .build();

            RecognitionAudio audio = RecognitionAudio.newBuilder()
                    .setContent(audioBytes)
                    .build();

            RecognizeResponse response =
                    speechClient.recognize(config, audio);

            if (response.getResultsList().isEmpty()) {
                throw new RuntimeException("No speech detected. Please speak clearly.");
            }

            SpeechRecognitionResult result = response.getResultsList().get(0);

            if (result.getAlternativesList().isEmpty()) {
                throw new RuntimeException("Speech could not be recognized.");
            }

            SpeechRecognitionAlternative alternative =
                    result.getAlternativesList().get(0);


            SpeechToTextResponse dto = new SpeechToTextResponse();
            dto.setText(alternative.getTranscript());
            dto.setDetectedLanguage("auto");
            dto.setConfidence(alternative.getConfidence());

            return dto;

        } catch (Exception e) {
            throw new RuntimeException("Speech-to-Text failed", e);
        }
    }
}
