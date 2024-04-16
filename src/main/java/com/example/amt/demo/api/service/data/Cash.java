package com.example.amt.demo.api.service.data;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

import static com.example.amt.demo.api.service.constatns.Constants.AMOUNT;
import static com.example.amt.demo.api.service.constatns.Constants.CUR;

@Data
@AllArgsConstructor
public class Cash {
    @Pattern(
            regexp = CUR,
            message = "The currency is indicated incorrectly"
    )
    String currency;

    @Positive(message = "You must enter a positive amount.")
    @Pattern(
            regexp = AMOUNT,
            message = "Invalid amount."
    )
    String amount;
}
