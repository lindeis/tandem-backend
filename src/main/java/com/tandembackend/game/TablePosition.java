package com.tandembackend.game;

import com.tandembackend.exception.InvalidTablePositionException;

import java.util.Locale;

public enum TablePosition {
    WHITE1, WHITE2, BLACK1, BLACK2;

    public static TablePosition fromString(String text) throws InvalidTablePositionException {
        String uppercaseText = text.toUpperCase(Locale.ROOT);
        if (uppercaseText.equals("WHITE1")) {
            return TablePosition.WHITE1;
        }
        if (uppercaseText.equals("WHITE2")) {
            return TablePosition.WHITE2;
        }
        if (uppercaseText.equals("BLACK1")) {
            return TablePosition.BLACK1;
        }
        if (uppercaseText.equals("BLACK2")) {
            return TablePosition.BLACK2;
        }
        throw new InvalidTablePositionException("\"" + text + "\" is not a valid table position.");
    }
}