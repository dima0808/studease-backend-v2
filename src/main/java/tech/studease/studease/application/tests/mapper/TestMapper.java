package tech.studease.studease.application.tests.mapper;

import static tech.studease.studease.common.util.TestUtils.getFinishedSessions;
import static tech.studease.studease.common.util.TestUtils.getMaxScore;
import static tech.studease.studease.common.util.TestUtils.getQuestionsCount;
import static tech.studease.studease.common.util.TestUtils.getStartedSessions;

import java.util.List;
import java.util.Set;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import tech.studease.studease.api.tests.dto.TestDto;
import tech.studease.studease.api.tests.dto.TestInfo;
import tech.studease.studease.api.tests.dto.TestListInfo;
import tech.studease.studease.application.questions.mapper.QuestionMapper;
import tech.studease.studease.application.samples.mapper.SampleMapper;
import tech.studease.studease.domain.questions.Question;
import tech.studease.studease.domain.samples.Sample;
import tech.studease.studease.domain.tests.Test;

@Mapper(componentModel = "spring")
public abstract class TestMapper {

  @Autowired protected QuestionMapper questionMapper;
  @Autowired protected SampleMapper sampleMapper;

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "questions", ignore = true)
  @Mapping(target = "samples", ignore = true)
  @Mapping(target = "sessions", ignore = true)
  @Mapping(target = "author", ignore = true)
  public abstract Test toTest(TestDto testDto);

  public void mapQuestionsAndSamples(Test test, TestDto testDto) {
    if (testDto.getQuestions() != null && !testDto.getQuestions().isEmpty()) {
      Set<Question> questions = questionMapper.toQuestion(testDto.getQuestions());
      questions.forEach(q -> q.setTest(test));
      test.setQuestions(questions);
    }
    if (testDto.getSamples() != null && !testDto.getSamples().isEmpty()) {
      Set<Sample> samples = sampleMapper.toSample(testDto.getSamples());
      samples.forEach(s -> s.setTest(test));
      test.setSamples(samples);
    }
  }

  @AfterMapping
  protected void afterToTest(@MappingTarget Test test, TestDto testDto) {
    mapQuestionsAndSamples(test, testDto);
    test.setSessions(List.of());
  }

  public TestInfo toTestInfo(Test test, boolean isAdmin) {
    return TestInfo.builder()
        .id(test.getId().toString())
        .name(test.getName())
        .openDate(test.getOpenDate())
        .deadline(test.getDeadline())
        .minutesToComplete(test.getMinutesToComplete())
        .maxScore(
            test.getMaximumScore() != null
                ? test.getMaximumScore()
                : getMaxScore(test.getQuestions(), test.getSamples()))
        .questionsCount(getQuestionsCount(test.getQuestions(), test.getSamples()))
        .startedSessions(isAdmin ? getStartedSessions(test.getSessions()) : null)
        .finishedSessions(isAdmin ? getFinishedSessions(test.getSessions()) : null)
        .build();
  }

  public List<TestInfo> toTestInfo(List<Test> tests, boolean isAdmin) {
    return tests.stream().map((test) -> toTestInfo(test, isAdmin)).toList();
  }

  public TestListInfo toTestListInfo(List<Test> tests) {
    return TestListInfo.builder().tests(toTestInfo(tests, true)).build();
  }
}
