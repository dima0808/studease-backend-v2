package tech.studease.studease.api.sessions.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.studease.studease.domain.users.Credentials;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseEntryRequestDto {

  private Credentials credentials;

  private List<Long> answerIds;
  private String answerContent;
}
