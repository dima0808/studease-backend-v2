package tech.studease.studease.application.questions;

import java.util.List;
import java.util.UUID;
import tech.studease.studease.api.questions.dto.QuestionDto;
import tech.studease.studease.api.questions.dto.QuestionListDto;

public interface QuestionService {

  QuestionListDto createAll(Long collectionId, List<QuestionDto> questionDtos);

  QuestionListDto findByTestId(UUID testId);

  QuestionListDto findByCollectionId(Long collectionId);
}
