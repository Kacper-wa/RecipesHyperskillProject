package com.example.recipeshyperskill.model.mapper;

import com.example.recipeshyperskill.model.dto.RecipeDto;
import org.mapstruct.*;
import com.example.recipeshyperskill.model.entity.Recipe;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RecipeMapper {

    @Mapping(target = "date", ignore = true)
    Recipe toEntity(RecipeDto recipeDto);

    RecipeDto toDto(Recipe recipe);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Recipe partialUpdate(RecipeDto recipeDto, @MappingTarget Recipe recipe);

    List<RecipeDto> toDtoList(List<Recipe> recipes);
}
