package tech.studease.studease.application.collections;

import tech.studease.studease.api.collections.dto.CollectionDeleteRequestDto;
import tech.studease.studease.api.collections.dto.CollectionDto;
import tech.studease.studease.api.collections.dto.CollectionInfo;
import tech.studease.studease.api.collections.dto.CollectionListInfo;

public interface CollectionService {

  CollectionListInfo findAll();

  CollectionInfo findById(Long collectionId);

  CollectionInfo create(CollectionDto collectionDto);

  void deleteById(Long collectionId);

  void deleteAllByIds(CollectionDeleteRequestDto request);
}
