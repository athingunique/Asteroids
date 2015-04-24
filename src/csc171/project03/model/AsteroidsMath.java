package csc171.project03.model;

/*
 * AsteroidsMath.java
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
 * A utility class containing some useful calculations
 */
public abstract class AsteroidsMath {

    /**
     * Returns a psuedo random double between the passed min and max values
     * @param min the min value
     * @param max the max value
     * @return the random double in the range {min, max}
     */
    public static double randRange(double min, double max) {
        return min + (max * Math.random());
    }

    /**
     * Gets the separation distance between two points
     * @param position0 point one
     * @param position1 point two
     * @return the separation distance
     */
    public static double getSeparation(Movement position0, Movement position1) {
        double x0 = position0.getX();
        double y0 = position0.getY();

        double x1 = position1.getX();
        double y1 = position1.getY();

        double squared = Math.pow(x1 - x0, 2) + Math.pow(y1 - y0, 2);
        return Math.sqrt(squared);
    }
}
