package csc171.project03.model;

/*
 * AngularMovement.java
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
 * A subclass of Movement that uses an angular component
 */
public class AngularMovement extends Movement {

    /**
     * Creates a new AngularMovement
     * @param a the angular compontent
     */
    public AngularMovement(double a) {
        super(a);
    }

    /**
     * Returns the next step of the passed Movement object according to the params of this Movement object
     * @param movement the object to operate on
     * @param timeStep the time step to operate over
     */
    @Override
    public void nextDelta(Movement movement, long timeStep) {
        super.angularDerivative(movement, timeStep);
    }
}
