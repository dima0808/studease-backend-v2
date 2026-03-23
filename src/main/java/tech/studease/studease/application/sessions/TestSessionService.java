package tech.studease.studease.application.sessions;

import java.util.UUID;
import tech.studease.studease.api.questions.dto.QuestionDto;
import tech.studease.studease.api.sessions.dto.ResponseEntryRequestDto;
import tech.studease.studease.api.sessions.dto.TestSessionDto;
import tech.studease.studease.api.sessions.dto.TestSessionListDto;
import tech.studease.studease.domain.users.Credentials;

public interface TestSessionService {

  TestSessionListDto findByTestId(UUID testId);

  TestSessionListDto findByTestIdAndCredentials(UUID testId, Credentials credentials);

  TestSessionDto findByTestIdAndCredentialsForStudent(UUID testId, Credentials credentials);

  QuestionDto startTestSession(UUID testId, Credentials credentials);

  QuestionDto getCurrentQuestion(UUID testId, Credentials credentials);

  QuestionDto nextQuestion(UUID testId, ResponseEntryRequestDto responseEntryRequestDto);

  TestSessionDto finishTestSession(UUID testId, ResponseEntryRequestDto responseEntryRequestDto);

  TestSessionDto forceEndTestSession(Long testSessionId);
}
