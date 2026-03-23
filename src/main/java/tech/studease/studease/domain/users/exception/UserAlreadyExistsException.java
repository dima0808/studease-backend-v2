package tech.studease.studease.domain.users.exception;

public class UserAlreadyExistsException extends RuntimeException {

  private static final String MESSAGE = "User with email '%s' already exists";

  public UserAlreadyExistsException(String username) {
    super(String.format(MESSAGE, username));
  }
}
