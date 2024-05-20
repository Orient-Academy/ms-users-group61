package az.edu.orient.model;

import az.edu.orient.validation.ValidCountry;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {
  private Long id;
  @NotNull(message = "firstName is required")
  private String firstName;
  @NotNull(message = "lastName is required")
  private String lastName;
  @ValidCountry
  private String country;
}
