package az.edu.orient.service;

import org.springframework.stereotype.Service;

import az.edu.orient.entity.UserEntity;
import az.edu.orient.mapper.UserMapper;
import az.edu.orient.model.UserDto;
import az.edu.orient.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public UserDto addUser(UserDto userDto) {
    UserEntity userEntity = UserMapper.INSTANCE.toEntity(userDto);
    UserEntity saved = userRepository.save(userEntity);
    return UserMapper.INSTANCE.toDto(saved);
  }
}
