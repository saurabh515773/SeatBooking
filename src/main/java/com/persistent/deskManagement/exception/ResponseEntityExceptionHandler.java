package com.persistent.deskManagement.exception;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

public abstract class ResponseEntityExceptionHandler {

    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            MethodArgumentNotValidException.class,
            MissingServletRequestPartException.class,
            BindException.class,
            NoHandlerFoundException.class,
            AsyncRequestTimeoutException.class,
        })
    @Nullable
    public final ResponseEntity<Object> handleException(Exception ex, HttpServletResponse request) throws Exception {
        CustomErrorResponse customError = new CustomErrorResponse();

        if (ex instanceof HttpRequestMethodNotSupportedException) {
    		customError.setTimestamp(LocalDateTime.now());
    		customError.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
    		customError.setError(ex.getMessage());
    		
    		return new ResponseEntity<>(customError, HttpStatus.METHOD_NOT_ALLOWED);
        }
        if (ex instanceof MissingServletRequestParameterException) {
    		customError.setTimestamp(LocalDateTime.now());
    		customError.setStatus(HttpStatus.BAD_REQUEST.value());
    		customError.setError(ex.getMessage());
    		
    		return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);
        }
        else if (ex instanceof HttpMediaTypeNotSupportedException) {
     		customError.setTimestamp(LocalDateTime.now());
     		customError.setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
     		customError.setError(ex.getMessage());
     		
     		return new ResponseEntity<>(customError, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }else {
      		customError.setTimestamp(LocalDateTime.now());
      		customError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      		customError.setError(ex.getMessage());
      		
      		return new ResponseEntity<>(customError, HttpStatus.INTERNAL_SERVER_ERROR);
        }        
    }
}
