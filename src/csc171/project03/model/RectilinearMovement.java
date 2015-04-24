package csc171.project03.model;

/*
 * RectilinearMovement.java
 * Version 1.0
 * Copyright Evan Baker
 * Course: CSC 171 SPRING 2015
 * Assignment: LAB 01
 * Author: Evan Baker
 * Lab Session: TR 0940-1055
 * Lab TA: Stephen Cohen
 * Last Revised: 4/22/15
 */

/**
 * A subclass of Movement that uses rectilinear components.
 */
public class RectilinearMovement extends Movement {

    /**
     * Creates a new RectilinearMovement.
     * @param x the x component.
     * @param y the y component.
     */
    public RectilinearMovement(double x, double y) {
        super(x, y);
    }

    /**
     * Returns the next step of the passed Movement object according to the params of this Movement object
     * @param movement the object to operate on
     * @param timeStep the time step to operate over
     */
    @Override
    public void nextDelta(Movement movement, long timeStep) {
        super.rectilinearDerivative(movement, timeStep);
    }

    public static Movement composeVector(double angle, double magnitude) {

        angle %= (2 * Math.PI); // Wrap the angle around so that it's between {0, 2π}

        double xDecomp = Math.abs(Math.cos(angle));
        double yDecomp = Math.abs(Math.sin(angle));

        boolean isRight = !(angle > (Math.PI / 2) && angle < ((3 * Math.PI) / 2));
        boolean isTop = (angle > 0 && angle < Math.PI);

        xDecomp *= !isRight ? -1 : 1;
        yDecomp *= !isTop ? -1 : 1;

        double xComp = xDecomp * magnitude;
        double yComp = yDecomp * magnitude;

        return new RectilinearMovement(xComp, yComp);
    }
}
