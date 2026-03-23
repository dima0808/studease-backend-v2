package tech.studease.studease.application.questions.impl;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import tech.studease.studease.api.questions.dto.QuestionListDto;
import tech.studease.studease.application.questions.OpenAIService;
import tech.studease.studease.domain.questions.QuestionType;

@Service
public class OpenAIServiceImpl implements OpenAIService {

  private final ChatClient chatClient;

  @Value("classpath:templates/get-questions-for-test.st")
  private Resource questionsPrompt;

  public OpenAIServiceImpl(ChatClient.Builder chatClientBuilder) {
    this.chatClient = chatClientBuilder.build();
  }

  @Override
  public QuestionListDto generateQuestions(
      String theme, QuestionType questionType, int points, int questionsCount) {
    QuestionListDto questions =
        chatClient
            .prompt()
            .user(
                userSpec ->
                    userSpec
                        .text(questionsPrompt)
                        .param("theme", theme)
                        .param("questionType", questionType.getDisplayName())
                        .param("difficulty", points < 2 ? "easy" : points < 4 ? "medium" : "hard")
                        .param("questionsCount", String.valueOf(questionsCount)))
            .call()
            .entity(QuestionListDto.class);
    if (questions == null) {
      throw new RuntimeException("Failed to generate questions");
    }
    questions.getQuestions().forEach(question -> question.setPoints(points));
    return questions;
  }
}
