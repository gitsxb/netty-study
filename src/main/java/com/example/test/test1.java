package com.example.test;

import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.protobuf.ByteString;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.google.auth.oauth2.ServiceAccountCredentials;

public class test1 {
    public static void main(String[] args) throws IOException {
        // 使用服务账户密钥文件进行身份验证
        String keyFilePath = "path/to/your/service-account-file.json";
        ServiceAccountCredentials credentials = ServiceAccountCredentials
                .fromStream(new FileInputStream(keyFilePath));
        SpeechClient speechClient = SpeechClient.create();

        // 设置音频文件路径和配置参数
        String audioFilePath = "path/to/your/audio-file.wav"; // 或其他支持的音频格式如 flac, mp3 等
        RecognitionConfig config = RecognitionConfig.newBuilder()
                .setEncoding(AudioEncoding.LINEAR16) // 设置音频编码格式，例如 LINEAR16, FLAC, MULAW, AMR, AMR_WB 等
                .setSampleRateHertz(16000) // 设置采样率，根据你的音频文件设置正确的值
                .setLanguageCode("en-US") // 设置语言代码，例如 "en-US" 为英语，"zh-CN" 为中文等
                .build();
        RecognitionAudio audio = RecognitionAudio.newBuilder()
                .setUri("file://" + audioFilePath) // 直接使用文件路径或使用setContent方法上传音频内容字节流
                // .setContent(ByteString.copyFrom(Files.readAllBytes(Paths.get(audioFilePath)))) // 如果需要直接上传内容而非文件路径方式
                .build();

        // 执行语音识别操作
        List<SpeechRecognitionResult> results = speechClient.recognize(config, audio).getResultsList();
        for (SpeechRecognitionResult result : results) {
            SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
            System.out.printf("Transcript: %s%n", alternative.getTranscript());
        }
        speechClient.close(); // 关闭客户端连接
    }
}