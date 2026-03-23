package tech.studease.studease.application.questions;

import tech.studease.studease.api.questions.dto.QuestionListDto;
import tech.studease.studease.domain.questions.QuestionType;

public interface OpenAIService {

  QuestionListDto generateQuestions(
      String theme, QuestionType questionsType, int points, int questionCount);
}
