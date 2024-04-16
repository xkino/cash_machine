package com.example.amt.demo.api.service.data;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import static com.example.amt.demo.api.service.constatns.Constants.CUR;
import static com.example.amt.demo.api.service.constatns.Constants.NOTE_VALUE;

@Data
@AllArgsConstructor
public class NoteDto {
    @Pattern(
            regexp = CUR,
            message = "The currency is indicated incorrectly"
    )
    String currency;

    @Pattern(
            regexp = NOTE_VALUE,
            message = "Valid values are 10n and 5*10n, 0<=n<=3"
    )
    @NonNull
    String value;

    @Positive(message = "You must enter a positive number.")
    int number;
}
