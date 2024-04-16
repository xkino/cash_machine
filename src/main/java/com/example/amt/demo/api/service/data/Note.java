package com.example.amt.demo.api.service.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Note {
    String currency;
    int value;
    int number;
}
