package com.ssafy.server.model.service;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@Service
public class ChatGptService {

    private final OpenAiChatModel openAiChatModel;

    public ChatGptService(OpenAiChatModel openAiChatModel) {
        this.openAiChatModel = openAiChatModel;
    }

    public String analyze(String string) {
        System.out.println("chatgptService:"+ string);
        return openAiChatModel.call(string);
    }
}
