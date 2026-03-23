package tech.studease.studease.domain.collections.exception;

import jakarta.persistence.EntityNotFoundException;

public class CollectionNotFoundException extends EntityNotFoundException {

  private static final String MESSAGE = "Collection with id '%d' not found";

  public CollectionNotFoundException(Long id) {
    super(String.format(MESSAGE, id));
  }
}
