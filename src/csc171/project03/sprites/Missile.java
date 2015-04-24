package csc171.project03.sprites;

/*
 * Missile.java
 * Version 1.0
 * Copyright Evan Baker
 * Course: CSC 171 SPRING 2015
 * Assignment: PROJECT 03
 * Author: Evan Baker
 * Lab Session: TR 0940-1055
 * Lab TA: Stephen Cohen
 * Last Revised: 4/23/15
 */


import csc171.project03.model.AngularMovement;
import csc171.project03.model.Movement;
import csc171.project03.model.RectilinearMovement;

import java.awt.*;

/**
 * A missile class. Missiles destroy {@link Asteroid}.
 * Uses a Factory pattern (private constructor, builds in #from())
 */
public class Missile extends AbstractSprite {

    /**
     * The velocity that Missiles travel with.
     */
    private static final double VEL = 300;

    /**
     * The size of Missiles.
     */
    private static final double SIZE = 2;

    /**
     * Creates a new Missile.
     * @param collisionRadius the collision radius.
     * @param rotation the inital angular position.
     * @param angularVelocity the initial angular velocity.
     * @param angularAcceleration the initial angular acceleration.
     * @param position the initial rectilinear position.
     * @param linearVelocity the initial rectilinear velocity.
     * @param linearAcceleration the initial rectilinear acceleration.
     */
    private Missile(double collisionRadius, Movement rotation, Movement angularVelocity, Movement angularAcceleration, Movement position, Movement linearVelocity, Movement linearAcceleration) {
        super(collisionRadius, rotation, angularVelocity, angularAcceleration, position, linearVelocity, linearAcceleration);
    }

    /**
     * Public factory method. Builds a Missile originating from the passed {@link Spaceship}.
     * @param origin the Missile origin.
     * @return the constructed Missile.
     */
    public static Missile from(AbstractSprite origin) {
        Movement rotation = new AngularMovement(origin.rotation.getA());
        Movement angularVelocity = new AngularMovement(0);
        Movement angularAcceleration = new AngularMovement(0);

        Movement position = new RectilinearMovement(origin.position.getX(), origin.position.getY());

        double angle = rotation.getA();
        double vx = VEL * Math.cos(angle);
        double vy = VEL * Math.sin(angle);

        Movement linearVelocity = new RectilinearMovement(vx, vy);
        Movement linearAcceleration = new RectilinearMovement(0, 0);

        return new Missile(SIZE, rotation, angularVelocity, angularAcceleration, position, linearVelocity, linearAcceleration);
    }

    /**
     * Draws this Missile.
     * @param g is the Graphics to paint with.
     */
    @Override
    void drawSprite(Graphics g) {
        double centerX = position.getX();
        double centerY = position.getY();

        g.fillOval(
                (int) (centerX),
                (int) (centerY),
                (int) (2 * collisionRadius),
                (int) (2 * collisionRadius)
        );
    }

    /**
     * Destroys this missile.
     * @return an empty AbstractSprite Array, indicating to destroy this Sprite and not spawn any others
     */
    @Override
    AbstractSprite[] destroySprite() {
        return new AbstractSprite[0];
    }
}
