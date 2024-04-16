package com.example.amt.demo.api.service.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OperationStatus {
    public enum Status {
        Ok,
        Error
    }

    private Status status;
    private String message;

    public static OperationStatus ok(String message) {
        return new OperationStatus(Status.Ok, message);
    }

    public static OperationStatus error(String message) {
        return new OperationStatus(Status.Error, message);
    }
}
