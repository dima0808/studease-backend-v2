package tech.studease.studease.domain.collections.exception;

public class CollectionInUseException extends IllegalStateException {

  private static final String MESSAGE = "Collection with id '%d' is used by existing tests";

  public CollectionInUseException(Long id) {
    super(String.format(MESSAGE, id));
  }
}
