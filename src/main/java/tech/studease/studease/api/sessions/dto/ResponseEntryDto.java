package tech.studease.studease.api.sessions.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.studease.studease.api.questions.dto.QuestionDto;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseEntryDto {

  private QuestionDto question;
  private List<Long> answerIds;
  private String answerContent;
}
