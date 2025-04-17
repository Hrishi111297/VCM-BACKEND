package com.bill.tech.exception;

import static com.bill.tech.constants.MessageConstants.BAD_CREDENTIALS;
import static com.bill.tech.constants.MessageConstants.DUPLICATE_DATA_ERROR_MSG;
import static com.bill.tech.constants.MessageConstants.ERROR_MSG;
import static com.bill.tech.constants.MessageConstants.TOKEN_EXPIRED;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCause;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.security.InvalidKeyException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.bill.tech.payload.ApiError;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
@RestControllerAdvice
@Slf4j
public class GloabalExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFound ex) {
        ApiError apiError = new ApiError(false, ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            errors.add(fieldName + ": " + error.getDefaultMessage());
        });
        ApiError apiError = new ApiError(false, "Validation error", errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralException(Exception ex) {
        ApiError apiError = new ApiError(false, "An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentialsException(BadCredentialsException ex) {
        ApiError apiError = new ApiError(false, "Invalid credentials: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        //String message = "Data integrity violation: " + (ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage());
        ApiError apiError = new ApiError(false, "Duplicate Entry!!!!!", HttpStatus.CONFLICT);
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiError> handleExpiredJwtException(ExpiredJwtException ex) {
        ApiError apiError = new ApiError(false, "Token expired. Please login again.", HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ApiError> handleSignatureException(SignatureException ex) {
        ApiError apiError = new ApiError(false, "JWT signature is not valid.", HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ApiError> handleMalformedJwtException(MalformedJwtException ex) {
        ApiError apiError = new ApiError(false, "JWT token is malformed.", HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CRMException.class)
    protected ResponseEntity<ApiError> handleCRMException(CRMException exc) {
        log.info("Handling CRM Exception: {}", exc.getLocalizedMessage());
        
        // Get the original exception from CRMException
        Exception rootException = exc.getException();

        // Check if the root exception is a BadCredentialsException
        if (rootException instanceof BadCredentialsException) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Invalid credentials", Arrays.asList("Invalid username or password"));
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        } 
        // Check if the root exception is an InvalidKeyException
        else if (rootException instanceof InvalidKeyException) {
            ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, "Token expired", Arrays.asList("Your session has expired"));
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        }
        else if (rootException instanceof DataIntegrityViolationException) {
            ApiError apiError = new ApiError(HttpStatus.CONFLICT, "Duplicate Entry!!!!!", Arrays.asList("Duplicate Entry!!!!!"));
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        }
        // Check if the root exception is a MultipartException (e.g., file upload error)
        else if (rootException instanceof MultipartException) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "File upload error", Arrays.asList("Error processing the file upload"));
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        }
        // Check if the root exception is a MaxUploadSizeExceededException (file size exceeded)
        else if (rootException instanceof MaxUploadSizeExceededException) {
            ApiError apiError = new ApiError(HttpStatus.PAYLOAD_TOO_LARGE, "File size exceeds the limit", Arrays.asList("Uploaded file is too large"));
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        }
        // Check if the root exception is an EmptyFileException (file is empty)
        else if (rootException instanceof FileNotValidException) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,  exc.getLocalizedMessage(), Arrays.asList("The file you uploaded is Not Valid!!!!"));
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        }
        // Handle other exceptions generally
        else {
            ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", Arrays.asList(rootException.getMessage()));
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        }
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            errors.add(fieldName + ": " + violation.getMessage());
        });
        ApiError apiError = new ApiError(false, "Constraint violation", errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ApiError> handleSQLException(SQLException ex) {
        ApiError apiError = new ApiError(false, "Database error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex) {
        ApiError apiError = new ApiError(false, "Access denied: " + ex.getMessage(), HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }
    
    // Handle invalid endpoints (404 Not Found)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiError> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        log.error("No handler found for request: {}", ex.getMessage());
        ApiError apiError = new ApiError(false, "Endpoint not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    // Handle invalid HTTP methods (405 Method Not Allowed)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> handleMethodNotAllowedException(HttpRequestMethodNotSupportedException ex) {
        log.error("Invalid HTTP method: {}", ex.getMessage());
        ApiError apiError = new ApiError(false, "Method not allowed", HttpStatus.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(apiError, HttpStatus.METHOD_NOT_ALLOWED);
    }
    
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiError> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        log.error("File size exceeds the limit: {}", ex.getMessage());
        ApiError apiError = new ApiError(false, "File size exceeds the allowed limit (10MB).", HttpStatus.PAYLOAD_TOO_LARGE);
        return new ResponseEntity<>(apiError, HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ApiError> handleMultipartException(MultipartException ex) {
        log.error("Multipart error: {}", ex.getMessage());
        ApiError apiError = new ApiError(false, "Error occurred while processing the file upload.", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }
}
