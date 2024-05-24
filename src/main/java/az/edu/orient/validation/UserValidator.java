package az.edu.orient.validation;

import az.edu.orient.model.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDto userDto = (UserDto) target;
        if(userDto.getId() <= 0) {
            errors.rejectValue("id", "", "Id could not be negative number or Zero");
        }
    }
}
