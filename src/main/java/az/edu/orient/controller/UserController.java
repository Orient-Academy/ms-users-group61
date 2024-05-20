package az.edu.orient.controller;

import az.edu.orient.exception.OrientException;
import az.edu.orient.model.UserDto;
import az.edu.orient.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<UserDto> getUsers() throws OrientException {
    return userService.getUsers();
  }

  @GetMapping(path = "{id}")
  public UserDto getUserById(@PathVariable Long id) throws OrientException {
    return userService.getUserById(id);
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public UserDto addUser(@RequestBody UserDto userDto) {
    return userService.addUser(userDto);
  }

  @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public UserDto updateUser(@RequestBody UserDto userDto) throws OrientException {
    return userService.updateUser(userDto);
  }

  @DeleteMapping(path = "{id}")
  public void deleteById(@PathVariable(name = "id") Long id) throws OrientException {
    userService.deleteUser(id);
  }
}
