package tech.studease.studease.domain.users.exception;

import jakarta.persistence.EntityNotFoundException;
import tech.studease.studease.domain.users.Authority.AuthorityName;

public class AuthorityNotFoundException extends EntityNotFoundException {

  private static final String MESSAGE = "Authority with name '%s' not found";

  public AuthorityNotFoundException(AuthorityName authority) {
    super(String.format(MESSAGE, authority));
  }
}
