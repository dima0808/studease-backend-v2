package tech.studease.studease.domain.sessions.exception;

import jakarta.persistence.EntityNotFoundException;

public class TestSessionNotFoundException extends EntityNotFoundException {

  private static final String MESSAGE_BY_STUDENT = "Test session with student %s %s not found";
  public static final String MESSAGE_BY_SESSION_ID = "Test session with sessionId %d not found";

  public TestSessionNotFoundException(String studentGroup, String studentName) {
    super(String.format(MESSAGE_BY_STUDENT, studentGroup, studentName));
  }

  public TestSessionNotFoundException(Long sessionId) {
    super(String.format(MESSAGE_BY_SESSION_ID, sessionId));
  }
}
