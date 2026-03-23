package tech.studease.studease.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import tech.studease.studease.api.answers.dto.AnswerDto;
import tech.studease.studease.api.questions.dto.QuestionDto;

public class AnswersValidator implements ConstraintValidator<ValidAnswers, QuestionDto> {

  @Override
  public boolean isValid(QuestionDto questionDto, ConstraintValidatorContext context) {
    if ("essay".equalsIgnoreCase(questionDto.getType())) {
      return true;
    }
    if (questionDto.getAnswers() != null && questionDto.getAnswers().size() >= 2) {
      if ("matching".equalsIgnoreCase(questionDto.getType())) {
        return true;
      } else {
        return questionDto.getAnswers().stream().anyMatch(AnswerDto::getIsCorrect);
      }
    }
    return false;
  }
}
