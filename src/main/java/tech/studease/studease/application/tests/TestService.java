package tech.studease.studease.application.tests;

import java.util.UUID;
import tech.studease.studease.api.tests.dto.TestDeleteRequestDto;
import tech.studease.studease.api.tests.dto.TestDto;
import tech.studease.studease.api.tests.dto.TestInfo;
import tech.studease.studease.api.tests.dto.TestListInfo;

public interface TestService {

  TestListInfo findAll();

  TestInfo findById(UUID testId);

  TestInfo findByIdForStudent(UUID testId);

  TestInfo create(TestDto testDto);

  TestInfo update(UUID testId, TestDto testDto);

  void deleteById(UUID testId);

  void deleteAllByIds(TestDeleteRequestDto deleteRequest);
}
