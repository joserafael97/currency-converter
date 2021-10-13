package br.com.jrafael.infrastructure.handler;

import br.com.jrafael.infrastructure.exception.GenericBusinessException;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler {

    protected final Logger LOGGER = LogManager.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                   WebRequest request) {
        String type = "Object";
        if (ex.getRequiredType() != null) type = ex.getRequiredType().getName();
        String error = new StringBuilder()
                .append(ex.getName()).append(" must be of the type ")
                .append(type).toString();
        this.LOGGER.error(error);
        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GenericBusinessException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ResponseEntity<String> handleBusinessErrors(HttpServletRequest req, GenericBusinessException be) {
        ResponseEntity<String> responseEntity = new ResponseEntity<>(be.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        this.LOGGER.error(be.getMessage(), be);
        return responseEntity;
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ResponseBody
    public ResponseEntity<String> handleDataIntegrityViolationException(HttpServletRequest req, DataIntegrityViolationException be) {
        ResponseEntity<String> responseEntity = new ResponseEntity<>(be.getMessage(), HttpStatus.CONFLICT);
        this.LOGGER.error(be.getMessage(), be);
        return responseEntity;
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handleFeignStatusException(FeignException e, HttpServletResponse response) {
        ResponseEntity<String> responseEntity = new ResponseEntity<>(e.contentUTF8(), HttpStatus.resolve(e.status()) != null ? HttpStatus.resolve(e.status()) : HttpStatus.BAD_REQUEST);
        this.LOGGER.error(e.getMessage(), e);
        return responseEntity;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<CustomMessageError> handleValidationErrors(HttpServletRequest req,
                                                                  MethodArgumentNotValidException manvex) {

        BindingResult result = manvex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        ValidationError validationError = new ValidationError();

        String message = "";
        for (FieldError fieldError : fieldErrors) {
            String error = fieldError.getField() +": "+ fieldError.getCode();
            message = message.length() == 0 ? error : message + " and " + error;
            validationError.addFieldError(fieldError.getField(), fieldError.getCode());
        }
        this.LOGGER.error(message);
        return new ResponseEntity<>(new CustomMessageError(HttpStatus.BAD_REQUEST.value(), message) , HttpStatus.BAD_REQUEST);
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class InvalidField implements Serializable {

        private String field;

        private String message;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class ValidationError implements Serializable {

        private List<InvalidField> fieldErrors = new ArrayList<>();

        public void addFieldError(String path, String message) {
            InvalidField error = new InvalidField(path, message);
            fieldErrors.add(error);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class CustomMessageError implements Serializable {
        private int status_code;
        private String message;
    }
}
