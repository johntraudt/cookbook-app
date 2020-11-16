package learn.myCookbook.controllers;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.sql.SQLOutput;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<String> handleException(IllegalArgumentException ex) {
//        System.out.println(ex.getStackTrace());
//        return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(SQLException.class)
//    public ResponseEntity<String> handleException(SQLException ex) {
//        System.out.println(ex.getStackTrace());
//        return new ResponseEntity<String>("Whoops, something went wrong communicating with the database.", HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(DataAccessException.class)
//    public ResponseEntity<String> handleException(DataAccessException ex) {
//        System.out.println(ex.getStackTrace());
//        return new ResponseEntity<String>("Whoops, something is awry in the database.", HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleException(Exception ex) {
//        System.out.println(ex.getStackTrace());
//        return new ResponseEntity<String>("Sorry, we're not sure what went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
