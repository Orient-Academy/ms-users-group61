package az.edu.orient.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import az.edu.orient.model.UserDto;
import az.edu.orient.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<UserDto> getUsers(){
    return null;
  }

  @GetMapping(path = "{id}")
  public UserDto getUserById(@PathVariable Long id) {
    return null;
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public UserDto addUser(@RequestBody UserDto userDto) {
    return userService.addUser(userDto);
  }

  @DeleteMapping(path = "{id}")
  public void deleteById(@PathVariable(name = "id") Long id){

  }
}
