package tech.studease.studease.application.tests.impl;

import static tech.studease.studease.common.util.JwtUtils.getUserFromAuthentication;
import static tech.studease.studease.common.util.TestUtils.getFinishedSessions;
import static tech.studease.studease.common.util.TestUtils.getStartedSessions;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.studease.studease.api.tests.dto.TestDeleteRequestDto;
import tech.studease.studease.api.tests.dto.TestDto;
import tech.studease.studease.api.tests.dto.TestInfo;
import tech.studease.studease.api.tests.dto.TestListInfo;
import tech.studease.studease.application.tests.TestService;
import tech.studease.studease.application.tests.mapper.TestMapper;
import tech.studease.studease.domain.tests.Test;
import tech.studease.studease.domain.tests.TestRepository;
import tech.studease.studease.domain.tests.exception.ImmutableTestException;
import tech.studease.studease.domain.tests.exception.TestNotFoundException;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

  private final TestRepository testRepository;
  private final TestMapper testMapper;

  @Override
  public TestListInfo findAll() {
    return testMapper.toTestListInfo(
        testRepository.findByAuthorEmail(getUserFromAuthentication().getEmail()));
  }

  @Override
  public TestInfo findById(UUID testId) {
    Test test =
        testRepository.findById(testId).orElseThrow(() -> new TestNotFoundException(testId));
    if (!test.getAuthor().getEmail().equals(getUserFromAuthentication().getEmail())) {
      throw new TestNotFoundException(testId);
    }
    return testMapper.toTestInfo(test, true);
  }

  @Override
  public TestInfo findByIdForStudent(UUID testId) {
    Test test =
        testRepository.getTestById(testId).orElseThrow(() -> new TestNotFoundException(testId));
    return testMapper.toTestInfo(test, false);
  }

  @Override
  public TestInfo create(TestDto testDto) {
    Test test = testMapper.toTest(testDto);
    test.setAuthor(getUserFromAuthentication());
    return testMapper.toTestInfo(testRepository.save(test), true);
  }

  @Override
  public TestInfo update(UUID testId, TestDto testDto) {
    Test testToUpdate =
        testRepository.findById(testId).orElseThrow(() -> new TestNotFoundException(testId));
    if (!testToUpdate.getAuthor().getEmail().equals(getUserFromAuthentication().getEmail())) {
      throw new TestNotFoundException(testId);
    }

    if (getStartedSessions(testToUpdate.getSessions()) != 0) {
      throw new ImmutableTestException(testId, "has started sessions");
    }
    if (getFinishedSessions(testToUpdate.getSessions()) != 0) {
      throw new ImmutableTestException(testId, "has finished sessions");
    }

    testToUpdate.setName(testDto.getName());
    testToUpdate.setOpenDate(testDto.getOpenDate());
    testToUpdate.setDeadline(testDto.getDeadline());
    testToUpdate.setMinutesToComplete(testDto.getMinutesToComplete());
    testMapper.mapQuestionsAndSamples(testToUpdate, testDto);
    return testMapper.toTestInfo(testRepository.save(testToUpdate), true);
  }

  @Override
  @Transactional
  public void deleteById(UUID testId) {
    Test test =
        testRepository.findById(testId).orElseThrow(() -> new TestNotFoundException(testId));
    if (!test.getAuthor().getEmail().equals(getUserFromAuthentication().getEmail())) {
      throw new TestNotFoundException(testId);
    }
    testRepository.delete(test);
  }

  @Override
  @Transactional
  public void deleteAllByIds(TestDeleteRequestDto deleteRequest) {
    List<Test> tests = testRepository.findAllById(deleteRequest.getTestIds());
    if (tests.isEmpty()) {
      return;
    }
    tests.forEach(
        t -> {
          if (!t.getAuthor().getEmail().equals(getUserFromAuthentication().getEmail())) {
            throw new TestNotFoundException(t.getId());
          }
        });
    testRepository.deleteAll(tests);
  }
}
