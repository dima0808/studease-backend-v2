package tech.studease.studease.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import tech.studease.studease.api.answers.dto.AnswerDto;

public class AnswerValidator implements ConstraintValidator<ValidAnswer, AnswerDto> {

  @Override
  public boolean isValid(AnswerDto answerDto, ConstraintValidatorContext context) {
    if (answerDto == null) {
      return false;
    }

    boolean isContentFilled = answerDto.getContent() != null;
    boolean isLeftOptionFilled = answerDto.getLeftOption() != null;
    boolean isRightOptionFilled = answerDto.getRightOption() != null;
    boolean isIsCorrectFilled = answerDto.getIsCorrect() != null;

    return (isContentFilled && !isLeftOptionFilled && !isRightOptionFilled && isIsCorrectFilled)
        || (!isContentFilled && isLeftOptionFilled && isRightOptionFilled && !isIsCorrectFilled);
  }
}
