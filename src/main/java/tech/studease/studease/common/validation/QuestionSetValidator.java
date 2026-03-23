package tech.studease.studease.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import tech.studease.studease.api.tests.dto.TestDto;

@RequiredArgsConstructor
public class QuestionSetValidator implements ConstraintValidator<ValidQuestionSet, TestDto> {

  @Override
  public boolean isValid(TestDto testDto, ConstraintValidatorContext context) {
    return (testDto.getQuestions() != null && !testDto.getQuestions().isEmpty())
        || (testDto.getSamples() != null && !testDto.getSamples().isEmpty());
  }
}
