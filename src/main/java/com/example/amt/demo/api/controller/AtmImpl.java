package com.example.amt.demo.api.controller;

import com.example.amt.demo.api.service.data.Cash;
import com.example.amt.demo.api.service.data.Note;
import com.example.amt.demo.api.service.data.NoteDto;
import com.example.amt.demo.api.service.data.OperationStatus;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.example.amt.demo.api.service.constatns.Constants.*;


@RestController
@AllArgsConstructor
@RequestMapping(value = "/amt", produces = MediaType.APPLICATION_JSON_VALUE)
public class AtmImpl implements Atm {

    private final ArrayList<Note> notes;

    @Override
    @PostMapping("/add_notes")
    public OperationStatus addNotes(@Valid @RequestBody NoteDto note) {
        var message = "+ " + note.getCurrency() + " " + note.getValue() + " " + note.getNumber();
        var findNote = notes.stream()
                .filter(item -> item.getValue() == Integer.parseInt(note.getValue()) && item.getCurrency().equals(note.getCurrency()))
                .findFirst();
        if (findNote.isPresent()) {
            var currentNote = findNote.get();
            currentNote.setNumber(currentNote.getNumber() + note.getNumber());
        } else {
            notes.add(new Note(note.getCurrency(), Integer.parseInt(note.getValue()), note.getNumber()));
        }
        System.out.printf("+ %s %s %s \n", note.getCurrency(), note.getValue(), note.getNumber());
        return OperationStatus.ok(message);
    }

    @Override
    @PostMapping("/get_cash")
    public OperationStatus getCash(@Valid @RequestBody Cash cash) {
        var amount = Integer.parseInt(cash.getAmount());
        if (amount > getAvailableAmount(cash)) {
            return OperationStatus.error("Amount is unavailable.");
        }
        cutCash(amount, getAvailableCash(cash));
        return OperationStatus.ok("message");
    }

    @Override
    @PostMapping("/print_cash")
    public void printCash() {
        notes
                .sort(Comparator.comparing(Note::getCurrency)
                        .thenComparing(Note::getValue));
        for (Note note : notes) {
            var message = note.getCurrency() + " " + note.getValue() + " " + note.getNumber();
            System.out.println(ANSI_ITALIC + ANSI_GREEN + message + ANSI_RESET);
        }
    }

    private List<Note> getAvailableCash(Cash cash) {
        return notes.stream()
                .filter(item -> item.getCurrency().equals(cash.getCurrency()))
                .sorted(Comparator.comparing(Note::getValue).reversed()).toList();
    }

    private int getAvailableAmount(Cash cash) {
        return notes.stream()
                .filter(item -> item.getCurrency().equals(cash.getCurrency()))
                .reduce(0, (res, note) -> res + note.getNumber() * note.getValue(), Integer::sum);
    }

    private void cutCash(int amount, List<Note> notes) {
        List<Note> delete = new ArrayList<>();
        int resCut = amount;
        for (Note note : notes) {
            if (resCut != 0) {
                var value = note.getValue();
                if (resCut < value) {
                    continue;
                }

                var sumValue = value * note.getNumber();
                if (resCut < sumValue) {
                    var count = resCut / value;
                    note.setNumber(note.getNumber() - count);
                    System.out.printf("- %s %s %s \n", note.getCurrency(), note.getValue(), count);
                    resCut -= note.getValue() * count;
                    continue;
                } else {
                    delete.add(note);
                }

                resCut -= note.getValue() * note.getNumber();
            }
        }
        deleteNote(delete);
    }

    private void deleteNote(List<Note> deleteNotes) {
        for (Note note : deleteNotes) {
            System.out.printf("- %s %s %s \n", note.getCurrency(), note.getValue(), note.getNumber());
            notes.remove(note);
        }
    }
}

