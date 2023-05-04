package com.example.recipeshyperskill.model.mapper;

import com.example.recipeshyperskill.model.dto.UserDto;
import com.example.recipeshyperskill.model.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    User toEntity(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    UserDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy =
            NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserDto userDto, @MappingTarget User user);

    @Mapping(target = "id", ignore = true)
    User signUpToUser(UserDto userDto);
}