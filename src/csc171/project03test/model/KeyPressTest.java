package csc171.project03test.model;

/*
 * KeyPressTest.java
 * Version 1.0
 * Copyright Evan Baker
 * Course: CSC 171 SPRING 2015
 * Assignment: PROJECT 03
 * Author: Evan Baker
 * Lab Session: TR 0940-1055
 * Lab TA: Stephen Cohen
 * Last Revised: 4/23/15
 */

import csc171.project03.model.KeyPress;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class KeyPressTest {

    private static final int[] validKeys = new int[] {KeyEvent.VK_SPACE,
            KeyEvent.VK_LEFT,
            KeyEvent.VK_RIGHT,
            KeyEvent.VK_UP,
            KeyEvent.VK_DOWN,
            KeyEvent.VK_KP_LEFT,
            KeyEvent.VK_KP_RIGHT,
            KeyEvent.VK_KP_UP,
            KeyEvent.VK_KP_DOWN
    };

    public static void main(String[] args) {
        new KeyPressTest().keyPressTest();
    }

    void keyPressTest() {
        System.out.println("Testing that the KeyPress recognizes valid keys...");

        boolean pass = true;
        for (int i : validKeys) {
            if (KeyPress.isValidKeycode(i)) {
                System.out.println("Key event valid");
            } else {
                System.out.println("Key event invalid");
                pass = false;
            }
        }

        if (pass) {
            System.out.println("KeyPress passed");
        } else {
            System.out.println("KeyPress failed");
        }
    }
}
