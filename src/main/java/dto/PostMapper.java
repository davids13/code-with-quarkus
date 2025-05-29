package dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "cdi",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PostMapper {

    PostDto toDto(final Post post);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "lastModifiedDate", expression = "java(java.time.LocalDateTime.now())")
    Post toEntity(CreatePostDto dto);

    List<PostDto> toDtoList(final List<Post> posts);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "authorEmail", ignore = true)
    @Mapping(target = "lastModifiedDate", expression = "java(java.time.LocalDateTime.now())")
    void updateEntityFromDto(UpdatePostDto dto, @MappingTarget Post post);
}
