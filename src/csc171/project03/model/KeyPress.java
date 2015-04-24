package csc171.project03.model;

/*
 * KeyPress.java
 * Version 1.0
 * Copyright Evan Baker
 * Course: CSC 171 SPRING 2015
 * Assignment: PROJECT 03
 * Author: Evan Baker
 * Lab Session: TR 0940-1055
 * Lab TA: Stephen Cohen
 * Last Revised: 4/21/15
 */

import java.awt.event.KeyEvent;

/**
 * A convenience class containing some Key Press utilities.
 */

public abstract class KeyPress {

    /**
     * The acceptable key presses.
     */
    public enum VALID_KEYS {
        UP,
        LEFT,
        RIGHT,
        DOWN,
        SPACE
    }

    /**
     * Determines if the KeyEvent passed is one of the acceptable key triggers.
     * @param e the KeyEvent to check.
     * @return whether the KeyEvent is valid.
     */
    public static boolean isValidKey(KeyEvent e) {
        int keyCode = e.getKeyCode();
        return isValidKeycode(keyCode);
    }

    /**
     * Determines if the passed keyCode is an accepted key.
     * @param keyCode the keyCode to check.
     * @return whether the KeyEvent is valid.
     */
    public static boolean isValidKeycode(int keyCode) {
        return     keyCode == KeyEvent.VK_SPACE
                || keyCode == KeyEvent.VK_LEFT
                || keyCode == KeyEvent.VK_RIGHT
                || keyCode == KeyEvent.VK_UP
                || keyCode == KeyEvent.VK_DOWN
                || keyCode == KeyEvent.VK_KP_LEFT
                || keyCode == KeyEvent.VK_KP_RIGHT
                || keyCode == KeyEvent.VK_KP_UP
                || keyCode == KeyEvent.VK_KP_DOWN;
    }

    /**
     * Determines which VALID_KEY a KeyEvent is.
     * @param e the KeyEvent to triage.
     * @return the VALID_KEY.
     */
    public static VALID_KEYS whichKey(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_SPACE) {
            return VALID_KEYS.SPACE;
        }
        if (keyCode == KeyEvent.VK_LEFT ||
        keyCode == KeyEvent.VK_KP_LEFT) {
            return VALID_KEYS.LEFT;
        }
        if (keyCode == KeyEvent.VK_RIGHT ||
        keyCode == KeyEvent.VK_KP_RIGHT) {
            return VALID_KEYS.RIGHT;
        }
        if (keyCode == KeyEvent.VK_UP ||
        keyCode == KeyEvent.VK_KP_UP) {
            return VALID_KEYS.UP;
        }
        if (keyCode == KeyEvent.VK_DOWN ||
        keyCode == KeyEvent.VK_KP_DOWN) {
            return VALID_KEYS.DOWN;
        }
        return null;
    }
}
