package az.edu.orient.exception;

import org.springframework.http.HttpStatus;

public class NonNullableParamException extends OrientException{
    public NonNullableParamException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
