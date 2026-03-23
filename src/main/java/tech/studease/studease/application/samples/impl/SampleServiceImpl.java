package tech.studease.studease.application.samples.impl;

import static tech.studease.studease.common.util.JwtUtils.getUserFromAuthentication;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.studease.studease.api.samples.dto.SampleListDto;
import tech.studease.studease.application.samples.SampleService;
import tech.studease.studease.application.samples.mapper.SampleMapper;
import tech.studease.studease.domain.samples.SampleRepository;
import tech.studease.studease.domain.tests.Test;
import tech.studease.studease.domain.tests.TestRepository;
import tech.studease.studease.domain.tests.exception.TestNotFoundException;

@Service
@RequiredArgsConstructor
public class SampleServiceImpl implements SampleService {

  private final SampleRepository sampleRepository;
  private final TestRepository testRepository;
  private final SampleMapper sampleMapper;

  @Override
  public SampleListDto findByTestId(UUID testId) {
    Test test =
        testRepository.findById(testId).orElseThrow(() -> new TestNotFoundException(testId));
    if (!test.getAuthor().getEmail().equals(getUserFromAuthentication().getEmail())) {
      throw new TestNotFoundException(testId);
    }
    return sampleMapper.toSampleListDto(sampleRepository.findByTestId(testId));
  }
}
