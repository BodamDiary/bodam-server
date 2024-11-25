package com.ssafy.server.model.service;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@Service
public class ChatGptService {

    private static final String prompt = "너는 30년간 발달장애 아동의 행동과 성격을 분석한 박사야. 지금부터 너는 발달장애 아동의 보호자가 적은 일기를 읽을거야. 주어진 일기들의 내용을을 읽고 아이의 성향과 특징, 학습 내용, 성장 방향성 등을 분석해서 보고서를 작성해줘. 지금부터는 일기야.\n";

    private final OpenAiChatModel openAiChatModel;

    public ChatGptService(OpenAiChatModel openAiChatModel) {
        this.openAiChatModel = openAiChatModel;
    }

    public String analyze(String string) {
        String res = openAiChatModel.call(prompt + string);
        System.out.println("gpt service:" + res);
        return res;
    }
}
