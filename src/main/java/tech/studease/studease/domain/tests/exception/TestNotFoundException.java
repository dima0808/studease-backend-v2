package tech.studease.studease.domain.tests.exception;

import jakarta.persistence.EntityNotFoundException;
import java.util.UUID;

public class TestNotFoundException extends EntityNotFoundException {

  private static final String MESSAGE = "Test with id '%s' not found";

  public TestNotFoundException(UUID id) {
    super(String.format(MESSAGE, id));
  }
}
