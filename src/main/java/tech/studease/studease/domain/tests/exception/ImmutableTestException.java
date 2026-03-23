package tech.studease.studease.domain.tests.exception;

import java.util.UUID;

public class ImmutableTestException extends RuntimeException {

  private static final String MESSAGE = "Test with id '%s' is immutable: %s";

  public ImmutableTestException(UUID id, String reason) {
    super(String.format(MESSAGE, id, reason));
  }
}
