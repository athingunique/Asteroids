package csc171.project03test;

import csc171.project03test.model.*;

/*
 * TestAll.java
 * Version 1.0
 * Copyright Evan Baker
 * Course: CSC 171 SPRING 2015
 * Assignment: PROJECT 03
 * Author: Evan Baker
 * Lab Session: TR 0940-1055
 * Lab TA: Stephen Cohen
 * Last Revised: 4/24/15
 */

public class Test {
    public static void main(String[] args) {
        new Test().testAll();
    }

    void testAll() {
        AsteroidsMathTest.main(null);
        GameKeeperTest.main(null);
        KeyPressTest.main(null);
        MovementTest.main(null);
        TimerThreadTest.main(null);
    }
}
