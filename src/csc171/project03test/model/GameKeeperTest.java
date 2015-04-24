package csc171.project03test.model;

import csc171.project03.model.GameKeeper;

/*
 * GameKeeperTest.java
 * Version 1.0
 * Copyright Evan Baker
 * Course: CSC 171 SPRING 2015
 * Assignment: PROJECT 03
 * Author: Evan Baker
 * Lab Session: TR 0940-1055
 * Lab TA: Stephen Cohen
 * Last Revised: 4/23/15
 */
public class GameKeeperTest {

    public static void main(String[] args) {
        new GameKeeperTest().gameKeeperTest();
    }

    void gameKeeperTest() {
        System.out.println("Testing GameKeeper");

        boolean pass = true;

        GameKeeper.resetScore();
        GameKeeper.addScore();
        if (GameKeeper.getScore() == 1) {
            System.out.println("GameKeeper passed score test");
        } else {
            System.out.println("GameKeep failed score test");
            pass = false;
        }

        GameKeeper.addRunTime(60000);

        if (GameKeeper.shouldSpawnAsteroid(0)) {
            System.out.println("GameKeeper passed asteroid add test");
        } else {
            System.out.println("GameKeep failed asteroid add test");
            pass = false;
        }

        if (GameKeeper.shouldSpawnMissile(0)) {
            System.out.println("GameKeeper passed missile add test");
        } else {
            System.out.println("GameKeep failed missile add test");
            pass = false;
        }

        if (GameKeeper.shouldSpawnBomb(0)) {
            System.out.println("GameKeeper passed bomb add test");
        } else {
            System.out.println("GameKeep failed bomb add test");
            pass = false;
        }

        if (!pass) {
            System.out.println("GameKeeper failed tests");
        } else {
            System.out.println("GameKeeper passed tests");
        }
    }
}
