package pl.power.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class IdIsNullAdvice {
    @ResponseBody
    @ExceptionHandler(IdIsNullException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String idIsNullAdvice(IdIsNullException ex) {
        return ex.getMessage();
    }
}
