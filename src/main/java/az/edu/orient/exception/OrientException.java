package az.edu.orient.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OrientException extends RuntimeException {
    private final HttpStatus status;
    public OrientException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }
}
