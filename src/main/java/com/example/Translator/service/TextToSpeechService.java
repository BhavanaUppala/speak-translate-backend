package com.example.Translator.service;

import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Service;

@Service
public class TextToSpeechService {

    public byte[] synthesize(String text, String languageCode) {

        try (TextToSpeechClient ttsClient = TextToSpeechClient.create()) {

            SynthesisInput input = SynthesisInput.newBuilder()
                    .setText(text)
                    .build();

            VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
                    .setLanguageCode(languageCode)
                    .setSsmlGender(SsmlVoiceGender.NEUTRAL)
                    .build();

            AudioConfig audioConfig = AudioConfig.newBuilder()
                    .setAudioEncoding(AudioEncoding.MP3)
                    .setSpeakingRate(0.95)   // slower, clearer
                    .setPitch(0.0)           // natural
                    .build();


            SynthesizeSpeechResponse response =
                    ttsClient.synthesizeSpeech(input, voice, audioConfig);

            ByteString audioContents = response.getAudioContent();
            return audioContents.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Text-to-Speech failed", e);
        }
    }
}
