package com.devsuperior.dscatalog.controller.exception;

import com.devsuperior.dscatalog.exception.EntityNotFoundException;
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
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest request){
        //Instancia a minha classe padrão de erro e seta as informações
        StandardError error = new StandardError();
        error.setTimeStamp(Instant.now());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setError("Resource not found");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
