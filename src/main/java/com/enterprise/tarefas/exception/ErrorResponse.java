package com.enterprise.tarefas.exception;

import lombok.Setter;

@Setter
public class ErrorResponse {

    int status;
    String message;
    String timestamp;

}
