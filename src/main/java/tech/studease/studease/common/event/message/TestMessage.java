package tech.studease.studease.common.event.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import tech.studease.studease.api.questions.dto.QuestionDto;
import tech.studease.studease.api.sessions.dto.TestSessionDto;

@AllArgsConstructor
@Data
@Builder
public class TestMessage {

  private TestMessageType type;
  private String content;
  private TestSessionDto testSession;
  private QuestionDto question;
}
