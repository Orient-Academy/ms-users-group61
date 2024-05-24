package az.edu.orient.controller;

import az.edu.orient.exception.OrientException;
import az.edu.orient.model.UserDto;
import az.edu.orient.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final List<Validator> validators;

  @InitBinder private void initBinder(WebDataBinder binder) {
    Optional.ofNullable(binder.getTarget())
            .ifPresent(target ->
                    validators.stream()
                            .filter(v -> v.supports(target.getClass()))
                            .forEach(binder::addValidators)
            );
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<UserDto> getUsers() {
    return userService.getUsers();
  }

  @GetMapping(path = "{id}")
  public UserDto getUserById(@PathVariable Long id) {
    return userService.getUserById(id);
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public UserDto addUser(@Valid @RequestBody  UserDto userDto) {
    return userService.addUser(userDto);
  }

  @PutMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public UserDto updateUser(@PathVariable(name = "id") Long id, @RequestBody UserDto userDto) throws OrientException {
    userDto.setId(id);
    return userService.updateUser(userDto);
  }

  @DeleteMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public void deleteById(@PathVariable(name = "id") Long id) {
    userService.deleteUser(id);
  }
}
