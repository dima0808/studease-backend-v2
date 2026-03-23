package tech.studease.studease.api.collections;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.studease.studease.api.collections.dto.CollectionDeleteRequestDto;
import tech.studease.studease.api.collections.dto.CollectionDto;
import tech.studease.studease.api.collections.dto.CollectionInfo;
import tech.studease.studease.api.collections.dto.CollectionListInfo;
import tech.studease.studease.application.collections.CollectionService;

@RestController
@RequestMapping("/api/v1/admin/collections")
@RequiredArgsConstructor
public class CollectionController {

  private final CollectionService collectionService;

  @GetMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<CollectionListInfo> getAllCollections() {
    return ResponseEntity.ok(collectionService.findAll());
  }

  @GetMapping("{collectionId}")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<CollectionInfo> getCollectionInfoById(@PathVariable Long collectionId) {
    return ResponseEntity.ok(collectionService.findById(collectionId));
  }

  @PostMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<CollectionInfo> createCollection(
      @RequestBody @Valid CollectionDto collectionDto) {
    return ResponseEntity.status(HttpStatus.CREATED.value())
        .body(collectionService.create(collectionDto));
  }

  @DeleteMapping("{collectionId}")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Void> deleteCollection(@PathVariable Long collectionId) {
    collectionService.deleteById(collectionId);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Void> deleteCollections(
      @RequestBody @Valid CollectionDeleteRequestDto request) {
    collectionService.deleteAllByIds(request);
    return ResponseEntity.noContent().build();
  }
}
