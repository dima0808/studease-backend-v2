package tech.studease.studease.domain.users.exception;

import jakarta.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

  private static final String MESSAGE = "User with email '%s' not found";

  public UserNotFoundException(String username) {
    super(String.format(MESSAGE, username));
  }
}
