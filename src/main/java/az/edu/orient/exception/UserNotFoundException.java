package az.edu.orient.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserNotFoundException extends OrientException{
    public UserNotFoundException(String message) {
        super(message,HttpStatus.NOT_FOUND);
    }
}
