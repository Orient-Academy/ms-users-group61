package az.edu.orient.service;

import az.edu.orient.client.account.AccountClient;
import az.edu.orient.client.account.dto.AccountDto;
import az.edu.orient.entity.UserEntity;
import az.edu.orient.exception.OrientException;
import az.edu.orient.mapper.UserMapper;
import az.edu.orient.model.UserDto;
import az.edu.orient.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final AccountClient accountClient;
  private final RabbitMessagingTemplate rabbitMessagingTemplate;
  @Value("${spring.rabbitmq.queue.user}")
  private String queueName;

  public List<UserDto> getUsers() throws OrientException {
    List<UserEntity> users = userRepository.findAll();
    if (users.isEmpty())
      throw new OrientException("There is not any user yet!", HttpStatus.NOT_FOUND);
    return UserMapper.INSTANCE.toDto(users);
  }

  public UserDto getUserById(Long id) throws OrientException {
    UserEntity user = getUserEntityById(id);
    return UserMapper.INSTANCE.toDto(user);
  }

  public UserDto addUser(UserDto userDto) {
    UserEntity userEntity = UserMapper.INSTANCE.toEntity(userDto);
    userEntity.setId(null);
    UserEntity saved = userRepository.save(userEntity);

    AccountDto accountDto = AccountDto.builder()
            .userId(saved.getId())
            .build();

    accountClient.createAccount(accountDto);
    UserDto result = UserMapper.INSTANCE.toDto(saved);
    rabbitMessagingTemplate.convertAndSend(queueName, result);
    return result;
  }

  public UserDto updateUser(UserDto userDto) throws OrientException {
    Long id = userDto.getId();
    if (id == null)
      throw new OrientException("ID can not be null for updated user!", HttpStatus.BAD_REQUEST);
    UserEntity currentUserEntity = getUserEntityById(id);
    UserEntity updatedUserEntity = UserMapper.INSTANCE.toEntity(userDto);
    if (currentUserEntity.equals(updatedUserEntity))
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

}