package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.time.ZonedDateTime;



@Getter
@Setter
@AllArgsConstructor
public class ApiException {
    String message;
    HttpStatusCode code;
    ZonedDateTime zonedDateTime;
}
