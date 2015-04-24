package csc171.project03.view;

/*
 * SpaceFrame.java
 * Version 1.0
 * Copyright Evan Baker
 * Course: CSC 171 SPRING 2015
 * Assignment: PROJECT 03
 * Author: Evan Baker
 * Lab Session: TR 0940-1055
 * Lab TA: Stephen Cohen
 * Last Revised: 4/19/15
 */

import javax.swing.*;
import java.awt.*;

/**
 * The game Frame; an OS level window. Everything happens inside this.
 */

public class SpaceFrame extends JFrame {

    /**
     * Constructs and sets up a new JFrame window.
     */
    public SpaceFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Adds the passed Canvas to this JFrame as it's single child.
     * @param canvas the Canvas to add to this JFrame.
     * @return this.
     */
    public SpaceFrame addCanvas(Canvas canvas) {
        getContentPane().add(canvas);
        return this;
    }

    /**
     * Launches this window.
     */
    public void display() {
        setSize(500, 500);
        setVisible(true);
    }
}
