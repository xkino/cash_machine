package com.example.amt.demo.api.controller;

import com.example.amt.demo.api.service.data.Cash;
import com.example.amt.demo.api.service.data.NoteDto;
import com.example.amt.demo.api.service.data.OperationStatus;

public interface Atm {
    OperationStatus addNotes(NoteDto note);

    OperationStatus getCash(Cash cashDto);

    void printCash();
}
