package csc171.project03test.model;

import csc171.project03.model.AsteroidsMath;
import csc171.project03.model.Movement;
import csc171.project03.model.RectilinearMovement;

/*
 * AsteroidsMathTest.java
 * Version 1.0
 * Copyright Evan Baker
 * Course: CSC 171 SPRING 2015
 * Assignment: PROJECT 03
 * Author: Evan Baker
 * Lab Session: TR 0940-1055
 * Lab TA: Stephen Cohen
 * Last Revised: 4/23/15
 */
public class AsteroidsMathTest {

    public static void main(String[] args) {
        new AsteroidsMathTest().asteroidsMathTest();
    }

    void asteroidsMathTest() {
        System.out.println("Testing AsteroidsMath");

        System.out.println("Here's a random from 5 to 10: " + AsteroidsMath.randRange(5, 10));

        Movement m1 = new RectilinearMovement(0, 0);
        Movement m2 = new RectilinearMovement(0, 1);

        double separation = AsteroidsMath.getSeparation(m1, m2);

        if ((int) separation != 1) {
            System.out.println("Failed separation test");
        } else {
            System.out.println("Passed separation test");
        }
    }


}
