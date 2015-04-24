package csc171.project03.model;

/*
 * Movement.java
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
 * Represents a movement property.
 * Contains a direction as a set of vector components {x, y}, or contains an angular component (with assumed 0
 * magnitude due to how these are treated).
 */

public abstract class Movement {

    /**
     * The x component of the movement.
     */
    private double x;

    /**
     * The y component of the movement.
     */
    private double y;

    /**
     * The angular component of the movement.
     */
    private double a;

    /**
     * Creates a new Movement object with the passed params.
     * @param x the x component of the direction of the movement.
     * @param y the y component of the direction of the movement.
     */
    public Movement(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a new Movement object with the passed params.
     * @param a the angular component of the movement.
     */
    public Movement(double a) {
        this.a = a;
    }

    /**
     * @return the x component of the direction of the movement.
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the unit x component of the movement.
     * @param x the x component.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the y component of the direction of the movement.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the unit y component of the movement.
     * @param y the y component.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @return the angular component of the movement from the positive x axis (in radians).
     */
    public double getA() {
        return a;
    }

    /**
     * Sets the angular component of the movement.
     * @param a the angular component.
     */
    public void setA(double a) {
        this.a = a;
    }

    /**
     * Returns the magnitude of resultant of the rectilinear components of the movement.
     * @return the magnitude of the rectilinear movement.
     */
    public double getMagnitude() {
        return Math.sqrt( Math.pow(x, 2) + Math.pow(y, 2) );
    }

    public void damp() {
        x -= ( x / 50);
        y -= ( y / 50);
        a -= ( a / 18);
    }

    /**
     * Takes a movement object, operates on it as if *this* movement is its integrand.
     * @param movement the object to operate on.
     * @param timeStep the time step to operate over.
     */
    public abstract void nextDelta(Movement movement, long timeStep);

    /**
     * Specific nextDelta() for AngularMovements.
     * @param movement the object to operate on.
     * @param timeStep the time step to operate over.
     */
    void angularDerivative(Movement movement, long timeStep) {
        movement.setA(((a / 1000d) * timeStep) + movement.getA());
    }

    /**
     * Specific nextDelta() for RectilinearMovements.
     * @param movement the object to operate on.
     * @param timeStep the time step to operate over.
     */
    void rectilinearDerivative(Movement movement, long timeStep) {
        movement.setX(((x / 1000d) * timeStep) + movement.getX());
        movement.setY(((y / 1000d) * timeStep) + movement.getY());
    }
}
