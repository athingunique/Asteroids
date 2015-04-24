package csc171.project03.model;

/*
 * KeyEventCallback.java
 * Version 1.0
 * Copyright Evan Baker
 * Course: CSC 171 SPRING 2015
 * Assignment: PROJECT 03
 * Author: Evan Baker
 * Lab Session: TR 0940-1055
 * Lab TA: Stephen Cohen
 * Last Revised: 4/21/15
 */

/**
 * Interface to let the focused window alert the Controller to key press events.
 */

public interface KeyEventCallback {
    void onKeyPressed(KeyPress.VALID_KEYS key);
    void onKeyReleased(KeyPress.VALID_KEYS key);
}
