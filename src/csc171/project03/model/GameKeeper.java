package csc171.project03.model;

/*
 * GameKeeper.java
 * Version 1.0
 * Copyright Evan Baker
 * Course: CSC 171 SPRING 2015
 * Assignment: PROJECT 03
 * Author: Evan Baker
 * Lab Session: TR 0940-1055
 * Lab TA: Stephen Cohen
 * Last Revised: 4/22/15
 */

/**
 * This class holds current state data and has methods that return data based on the current game state.
 * The Controller delegates decisions to the GameKeeper so that it stays focused on controlling.
 */

public abstract class GameKeeper {

    /**
     * The current total runtime, in millis.
     */
    private static long runTimeMillis;

    /**
     * The time since the last Asteroid spawn, in millis.
     */
    private static long lastAsteroidSpawn;

    /**
     * The time since the last Missile spawn, in millis.
     */
    private static long lastMissileSpawn;

    /**
     * The time since the last Bomb spawn, in millis.
     */
    private static long lastBombSpawn;

    /**
     * The current score.
     */
    private static long score;

    /**
     * Resets the score to zero.
     */
    public static void resetScore() {
        GameKeeper.score = 0;
    }

    /**
     * @return the current score.
     */
    public static long getScore() {
        return GameKeeper.score;
    }

    /**
     * Increments the score by one.
     */
    public static void addScore() {
        GameKeeper.score++;
    }

    /**
     * Adds the passed amount to the total run time and object spawn timers.
     * @param millis the time to add, in millis.
     */
    public static void addRunTime(long millis) {
        runTimeMillis += millis;
        lastAsteroidSpawn += millis;
        lastMissileSpawn += millis;
        lastBombSpawn += millis;
    }

    /**
     * Algorithm to determine if a new Asteroid can be spawned.
     * @param currentAsteroidCount the current total number of Asteroids.
     * @return if a new Asteroid can be spawned.
     */
    public static boolean shouldSpawnAsteroid(int currentAsteroidCount) {
        boolean spawn = false;

        long rampMax = runTimeMillis > 30000 ? 20 : (20 * runTimeMillis) / 30000;

        if (currentAsteroidCount < rampMax) {

            if (currentAsteroidCount < 1) {
                spawn = true;
            }

            if (lastAsteroidSpawn > 500) {
                spawn = true;
            }
        }

        if (spawn) {
            lastAsteroidSpawn = 0;
        }

        return spawn;
    }

    /**
     * Algorithm to determine if a new Missile can be spawned.
     * @param currentMissileCount the current total number of Missiles
     * @return if a new Missile can be spawned.
     */
    public static boolean shouldSpawnMissile(int currentMissileCount) {
        boolean spawn = false;

        if (currentMissileCount < 20) {

            if (currentMissileCount < 1 || lastMissileSpawn > 200) {
                spawn = true;
            }
        }

        if (spawn) {
            lastMissileSpawn = 0;
        }

        return spawn;
    }

    /**
     * Algorithm to determine if a new Bomb can be spawned.
     * @param currentBombCount the current total number of Bombs
     * @return if a new Bomb can be spawned.
     */
    public static boolean shouldSpawnBomb(int currentBombCount) {
        boolean spawn = false;

        if (currentBombCount < 3) {
            if (currentBombCount < 1 || lastBombSpawn > 500) {
                spawn = true;
            }
        }

        if (spawn) {
            lastBombSpawn = 0;
        }

        return spawn;
    }
}
