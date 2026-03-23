package tech.studease.studease.domain.collections.exception;

public class CollectionAlreadyExistsException extends RuntimeException {

  public static final String MESSAGE = "Collection with name '%s' already exists";

  public CollectionAlreadyExistsException(String name) {
    super(String.format(MESSAGE, name));
  }
}
