package tech.studease.studease.domain.sessions.exception;

public class TestSessionAlreadyExistsException extends RuntimeException {

  public static final String MESSAGE = "Test session with student %s %s already exists";

  public TestSessionAlreadyExistsException(String studentGroup, String studentName) {
    super(String.format(MESSAGE, studentGroup, studentName));
  }
}
