package tech.studease.studease.application.collections.mapper;

import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tech.studease.studease.api.collections.dto.CollectionDto;
import tech.studease.studease.api.collections.dto.CollectionInfo;
import tech.studease.studease.api.collections.dto.CollectionListInfo;
import tech.studease.studease.application.questions.mapper.QuestionMapper;
import tech.studease.studease.domain.collections.Collection;

@Mapper(
    componentModel = "spring",
    uses = {QuestionMapper.class})
public interface CollectionMapper {

  @Mapping(target = "questionsCount", expression = "java(collection.getQuestions().size())")
  @Mapping(
      target = "usedInTests",
      expression = "java(collection.getSamples() != null ? collection.getSamples().size() : 0)")
  CollectionInfo toCollectionInfo(Collection collection);

  List<CollectionInfo> toCollectionInfo(List<Collection> collections);

  default CollectionListInfo toCollectionListInfo(List<Collection> collections) {
    return CollectionListInfo.builder().collections(toCollectionInfo(collections)).build();
  }

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "questions", source = "questions", qualifiedByName = "toQuestion")
  @Mapping(target = "samples", ignore = true)
  @Mapping(target = "author", ignore = true)
  Collection toCollection(CollectionDto collectionDto);

  @AfterMapping
  default void setQuestions(@MappingTarget Collection collection, CollectionDto collectionDto) {
    if (collectionDto.getQuestions() != null && !collectionDto.getQuestions().isEmpty()) {
      collection.getQuestions().forEach(q -> q.setCollection(collection));
    }
  }
}
