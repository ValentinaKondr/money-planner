package sf.financescontrol.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import sf.financescontrol.api.exception.ErrorMessage;
import sf.financescontrol.api.exception.NotFoundException;
import sf.financescontrol.api.exception.UnauthorizedException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class AdviceController {

    private static final Logger logger = LoggerFactory.getLogger(AdviceController.class);

    @ExceptionHandler(value = {UnauthorizedException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorMessage constraintException(UnauthorizedException ex, WebRequest request) {
        return ErrorMessage.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage constraintException(NotFoundException ex, WebRequest request) {
        return ErrorMessage.builder()
                .status(HttpStatus.NOT_FOUND)
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage constraintException(RuntimeException ex, WebRequest request) {
        logger.info("Exception: \n", ex);
        return ErrorMessage.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .build();
    }
}
