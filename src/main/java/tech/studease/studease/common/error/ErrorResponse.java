package tech.studease.studease.common.error;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {

  private int status;
  private String error;
  private String message;
  private String path;
}
