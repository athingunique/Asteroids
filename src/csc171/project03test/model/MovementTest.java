package csc171.project03test.model;

import csc171.project03.model.AngularMovement;
import csc171.project03.model.Movement;
import csc171.project03.model.RectilinearMovement;

/*
 * MovementTest.java
 * Version 1.0
 * Copyright Evan Baker
 * Course: CSC 171 SPRING 2015
 * Assignment: PROJECT 03
 * Author: Evan Baker
 * Lab Session: TR 0940-1055
 * Lab TA: Stephen Cohen
 * Last Revised: 4/23/15
 */

public class MovementTest {

    public static void main(String[] args) {
        new MovementTest().movementTest();
    }

    void movementTest() {
        int i = 1;

        Movement r = new RectilinearMovement(i, i);
        Movement a = new AngularMovement(i);

        if (r.getX() == i && r.getY() == i && a.getA() == i) {
            System.out.println("Movement passed tests");
        }
    }
}
