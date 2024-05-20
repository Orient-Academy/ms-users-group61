package az.edu.orient.service;

import az.edu.orient.entity.UserEntity;
import az.edu.orient.exception.OrientException;
import az.edu.orient.mapper.UserMapper;
import az.edu.orient.model.UserDto;
import az.edu.orient.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public List<UserDto> getUsers() throws OrientException {
    List<UserEntity> users = userRepository.findAll();
    if (users.isEmpty())
      throw new OrientException("There is not any user yet!", HttpStatus.NOT_FOUND);
    return users.stream().map(UserMapper.INSTANCE::toDto).toList();
  }

  public UserDto getUserById(Long id) throws OrientException {
    UserEntity user = getUserEntityById(id);
    return UserMapper.INSTANCE.toDto(user);
  }

  public UserDto addUser(UserDto userDto) {
    UserEntity userEntity = UserMapper.INSTANCE.toEntity(userDto);
    userEntity.setId(null);
    UserEntity saved = userRepository.save(userEntity);
    return UserMapper.INSTANCE.toDto(saved);
  }

  public UserDto updateUser(UserDto userDto) throws OrientException {
    Long id = userDto.getId();
    if (id == null)
      throw new OrientException("ID can not be null for updated user!", HttpStatus.BAD_REQUEST);
    UserEntity currentUserEntity = getUserEntityById(id);
    UserEntity updatedUserEntity = UserMapper.INSTANCE.toEntity(userDto);
    if (isUserEntitiesSame(currentUserEntity, updatedUserEntity))
      throw new OrientException("There is not change in updated user details!", HttpStatus.CONFLICT);
    UserEntity saved = userRepository.save(updatedUserEntity);
    return UserMapper.INSTANCE.toDto(saved);
  }

  public void deleteUser(Long id) throws OrientException {
    UserEntity user = getUserEntityById(id);
    userRepository.deleteById(id);
  }

  private UserEntity getUserEntityById(Long id) throws OrientException{
      return userRepository.findById(id)
            .orElseThrow(() -> new OrientException("User not found!", HttpStatus.NOT_FOUND));
  }

  private boolean isUserEntitiesSame(UserEntity user1, UserEntity user2) {
    return Objects.equals(user1.getId(), user2.getId()) &&
            Objects.equals(user1.getFirstName(), user2.getFirstName()) &&
            Objects.equals(user1.getLastName(), user2.getLastName());
  }
}