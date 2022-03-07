package com.banking.assignment.config;

import com.banking.assignment.exceptions.ConcurrentLoginException;
import com.banking.assignment.exceptions.NoSuchValueException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class CustomControllerAdviceTest {
    @InjectMocks
    private CustomControllerAdvice customControllerAdvice;
    @Test
    public void handleAccessDeniedExceptionTest()  {

        ResponseEntity<?> errorResponse = customControllerAdvice.handleAccessDeniedException(new AccessDeniedException("You are not authorized"));
        assertEquals(HttpStatus.UNAUTHORIZED, errorResponse.getStatusCode());
    }
    @Test
    public void handleNoSuchValeExceptionTest()  {
        ResponseEntity<?> errorResponse = customControllerAdvice.handleNoSuchValueException(new NoSuchValueException("No value present for searched criteria"));
        assertEquals(HttpStatus.OK, errorResponse.getStatusCode());
        assertEquals("No value present for searched criteria", errorResponse.getBody());
    }
    @Test
    public void handleConcurrentLoginExceptionTest()  {
        ResponseEntity<?> errorResponse = customControllerAdvice.handleConcurrentLoginException(new ConcurrentLoginException("admin"));
        assertEquals(HttpStatus.NOT_ACCEPTABLE, errorResponse.getStatusCode());
        assertEquals("admin is not allowed to do multiple logins", errorResponse.getBody());
    }

}
