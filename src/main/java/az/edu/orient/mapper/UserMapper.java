package az.edu.orient.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import az.edu.orient.entity.UserEntity;
import az.edu.orient.model.UserDto;

@Mapper
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  UserEntity toEntity(UserDto userDto);

  UserDto toDto(UserEntity userEntity);
}
