package dev.hydris.gptdirect;

import io.github.sashirestela.openai.SimpleOpenAI;
import io.github.sashirestela.openai.domain.chat.Chat;
import io.github.sashirestela.openai.domain.chat.ChatMessage;
import io.github.sashirestela.openai.domain.chat.ChatRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

public class GptController {

    private final String key;

    public GptController() throws IOException {
        String tempKey = Files.readString(Paths.get("/run/secrets/openai_key"), StandardCharsets.UTF_8);
        key = tempKey.replaceAll("\\s+", "");

    }

    public String generate(String system, String user) {
        SimpleOpenAI openAI = SimpleOpenAI.builder()
                .apiKey(key)
                .build();

        ChatRequest chatRequest = ChatRequest.builder()
                .model("gpt-4o")
                .message(ChatMessage.SystemMessage.of(system))
                .message(ChatMessage.UserMessage.of(user))
                .temperature(0.1)
                .maxTokens(1000)
                .build();

        CompletableFuture<Chat> futureChat = openAI.chatCompletions().create(chatRequest);
        Chat chatResponse = futureChat.join();

        return chatResponse.firstContent();
    }

}
