package com.hospitality_company.HotelPersonelManagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@ControllerAdvice
@ResponseBody
public class ApiControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void springHandleNotFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleException(Exception exception) {
        //ResponseEntity - a class that stores a message-string and an httpStatus
        HttpStatus badRequestStatus = HttpStatus.BAD_REQUEST;
        ResponseEntity<Object> requestError = new ResponseEntity<Object>(
                badRequestStatus, HttpStatus.valueOf(exception.getMessage()));
        return ResponseEntity.status(badRequestStatus).body(requestError);
    }
}
