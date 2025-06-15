package com.enterprise.tarefas.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter @Getter
public class ErrorResponse {

    int status;
    String message;
    LocalDateTime timestamp;

}
