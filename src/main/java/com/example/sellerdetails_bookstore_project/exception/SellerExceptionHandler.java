package com.example.sellerdetails_bookstore_project.exception;

import com.example.sellerdetails_bookstore_project.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class SellerExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        List<ObjectError> errorList = exception.getBindingResult().getAllErrors();
        List<String> error_message = errorList.stream()
                .map(objErr -> objErr.getDefaultMessage())
                .collect(Collectors.toList());
        ResponseDTO responseDTO = new ResponseDTO("Exception while processing REST request", error_message.toString());
        return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SellerException.class)
    public ResponseEntity<ResponseDTO> handleBookStoreException(SellerException exception){
        ResponseDTO resDTO = new ResponseDTO("Exception while processing User REST request", exception.getMessage());
        return new ResponseEntity(resDTO, HttpStatus.BAD_REQUEST);
    }
}
