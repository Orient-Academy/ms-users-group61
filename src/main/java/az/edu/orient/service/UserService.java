package az.edu.orient.service;

import az.edu.orient.entity.UserEntity;
import az.edu.orient.exception.NoChangeInUpdateException;
import az.edu.orient.exception.NonNullableParamException;
import az.edu.orient.exception.UserNotFoundException;
import az.edu.orient.mapper.UserMapper;
import az.edu.orient.model.UserDto;
import az.edu.orient.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserDto> getUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(UserMapper.INSTANCE::toDto).toList();
    }

    public UserDto getUserById(Long id) {
        UserEntity user = getUserEntityById(id);
        return UserMapper.INSTANCE.toDto(user);
    }

    public UserDto addUser(UserDto userDto) {
        UserEntity userEntity = UserMapper.INSTANCE.toEntity(userDto);
        userEntity.setId(null);
        UserEntity saved = userRepository.save(userEntity);
        return UserMapper.INSTANCE.toDto(saved);
    }

    public UserDto updateUser(UserDto userDto) {
        Long id = userDto.getId();
        if (id == null) {
            throw new NonNullableParamException("ID can not be null for updated user!");
        }
        UserEntity currentUserEntity = getUserEntityById(id);
        UserEntity updatedUserEntity = UserMapper.INSTANCE.toEntity(userDto);
        if (currentUserEntity.equals(updatedUserEntity)) {
            throw new NoChangeInUpdateException("There is not change in updated user details!");
        }
        UserEntity saved = userRepository.save(updatedUserEntity);
        return UserMapper.INSTANCE.toDto(saved);
    }

    public void deleteUser(Long id) {
        getUserEntityById(id);
        userRepository.deleteById(id);
    }

    private UserEntity getUserEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
    }
}