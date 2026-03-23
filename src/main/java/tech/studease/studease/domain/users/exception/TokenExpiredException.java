package tech.studease.studease.domain.users.exception;

public class TokenExpiredException extends RuntimeException {

  public static final String MESSAGE = "Token has expired";

  public TokenExpiredException() {
    super(MESSAGE);
  }
}
