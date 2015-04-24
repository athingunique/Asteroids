package csc171.project03.view;

import csc171.project03.model.KeyEventCallback;
import csc171.project03.model.KeyPress;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * SpaceCanvas.java
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
 * This is the Canvas on which the game is drawn.
 */

public class SpaceCanvas extends Canvas {

    /**
     * The {@link KeyEventCallback} for this Canvas to report key events to.
     */
    private KeyEventCallback keyEventCallback;

    /**
     * Constructs a new Canvas with a reference to the passed {@link KeyEventCallback}
     * @param keyEventCallback the callback to report key events to.
     */
    public SpaceCanvas(KeyEventCallback keyEventCallback) {
        this.keyEventCallback = keyEventCallback;
        setIgnoreRepaint(true);
        attachKeyListener();
    }

    /**
     * Attaches a new {@link KeyListener} to this Canvas to listen for a {@link KeyEvent}
     */
    private void attachKeyListener() {
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // do nothing
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // Capture the key press event
                if (KeyPress.isValidKey(e)) {

                    KeyPress.VALID_KEYS key = KeyPress.whichKey(e);
                    keyEventCallback.onKeyPressed(key);

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Capture the key released event
                // Capture the arrow keys
                if (KeyPress.isValidKey(e)) {

                    KeyPress.VALID_KEYS key = KeyPress.whichKey(e);
                    keyEventCallback.onKeyReleased(key);

                }
            }
        });
    }


}
