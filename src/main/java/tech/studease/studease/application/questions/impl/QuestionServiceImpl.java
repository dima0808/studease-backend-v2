package tech.studease.studease.application.questions.impl;

import static tech.studease.studease.common.util.JwtUtils.getUserFromAuthentication;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.studease.studease.api.questions.dto.QuestionDto;
import tech.studease.studease.api.questions.dto.QuestionListDto;
import tech.studease.studease.application.questions.QuestionService;
import tech.studease.studease.application.questions.mapper.QuestionMapper;
import tech.studease.studease.domain.collections.Collection;
import tech.studease.studease.domain.collections.CollectionRepository;
import tech.studease.studease.domain.collections.exception.CollectionNotFoundException;
import tech.studease.studease.domain.questions.Question;
import tech.studease.studease.domain.questions.QuestionRepository;
import tech.studease.studease.domain.tests.Test;
import tech.studease.studease.domain.tests.TestRepository;
import tech.studease.studease.domain.tests.exception.TestNotFoundException;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

  private final QuestionRepository questionRepository;
  private final CollectionRepository collectionRepository;
  private final TestRepository testRepository;
  private final QuestionMapper questionMapper;

  @Override
  public QuestionListDto createAll(Long collectionId, List<QuestionDto> questionDtos) {
    List<Question> questions = questionRepository.saveAll(questionMapper.toQuestion(questionDtos));
    return questionMapper.toQuestionListDto(questions);
  }

  @Override
  @Transactional
  public QuestionListDto findByTestId(UUID testId) {
    Test test =
        testRepository.findById(testId).orElseThrow(() -> new TestNotFoundException(testId));
    if (!test.getAuthor().getEmail().equals(getUserFromAuthentication().getEmail())) {
      throw new TestNotFoundException(testId);
    }
    Set<Question> questions = questionRepository.findByTestId(testId);
    questions.forEach(question -> Hibernate.initialize(question.getAnswers()));
    return questionMapper.toQuestionListDto(questions);
  }

  @Override
  @Transactional
  public QuestionListDto findByCollectionId(Long collectionId) {
    Collection collection =
        collectionRepository
            .findById(collectionId)
            .orElseThrow(() -> new CollectionNotFoundException(collectionId));
    if (!collection.getAuthor().getEmail().equals(getUserFromAuthentication().getEmail())) {
      throw new CollectionNotFoundException(collectionId);
    }
    Set<Question> questions = questionRepository.findByCollectionId(collectionId);
    questions.forEach(question -> Hibernate.initialize(question.getAnswers()));
    return questionMapper.toQuestionListDto(questions);
  }
}
