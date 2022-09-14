package com.devsuperior.dscatalog.controller.exception;

import com.devsuperior.dscatalog.service.exception.DatabaseException;
import com.devsuperior.dscatalog.service.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

//Essa anotação permite que permite que essa classe intercepte alguma exception
// que acontecer na camada de controller e trate ela
@ControllerAdvice
public class ControllerExceptionHandler {
    //Anotation tem que ter o nome da exceção para saber o tipo que ele vai tratar
    //Httpservlet request é uma classe que tem as informações da requisição http
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
        //Instancia a minha classe padrão de erro e seta as informações
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        StandardError error = new StandardError();
        error.setTimeStamp(Instant.now());
        error.setStatus(httpStatus.value());
        error.setError("Resource not found");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(error);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> entityNotFound(DatabaseException e, HttpServletRequest request){
        //Instancia a minha classe padrão de erro e seta as informações
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        StandardError error = new StandardError();
        error.setTimeStamp(Instant.now());
        error.setStatus(httpStatus.value());
        error.setError("Database exception");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(error);
    }
}
