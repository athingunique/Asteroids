package csc171.project03.sprites;

/*
 * Bomb.java
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
 * This defines a Bomb. It has zero rotation, velocities, and accelerations. After a certain amount of time it blows up,
 * spawning several missiles that travel radially.
 * Uses a Factory pattern.
 */
public class Bomb extends AbstractSprite {

    /**
     * The size of a Bomb.
     */
    private static final int SIZE = 7;

    /**
     * The fuse time of a Bomb.
     */
    private long maxAge = 3000;

    /**
     * Creates a new Bomb.
     * @param collisionRadius the collision radius.
     * @param rotation the inital angular position.
     * @param angularVelocity the initial angular velocity.
     * @param angularAcceleration the initial angular acceleration.
     * @param position the initial rectilinear position.
     * @param linearVelocity the initial rectilinear velocity.
     * @param linearAcceleration the initial rectilinear acceleration.
     */
    private Bomb(double collisionRadius, Movement rotation, Movement angularVelocity, Movement angularAcceleration, Movement position, Movement linearVelocity, Movement linearAcceleration) {
        super(collisionRadius, rotation, angularVelocity, angularAcceleration, position, linearVelocity, linearAcceleration);
    }

    /**
     * Factor method to construct a new Bomb dropped from the passed Spaceship.
     * @param spaceShip the Bomb origin.
     * @return a constructed Bomb.
     */
    public static Bomb from(AbstractSprite spaceShip) {
        Movement rotation = new AngularMovement(0);
        Movement angularVelocity = new AngularMovement(0);
        Movement angularAcceleration = new AngularMovement(0);

        Movement position = new RectilinearMovement(
                spaceShip.position.getX() - Spaceship.SPRITE_WIDTH,
                spaceShip.position.getY() - Spaceship.SPRITE_WIDTH);
        Movement linearVelocity = new RectilinearMovement(0, 0);
        Movement linearAcceleration = new RectilinearMovement(0, 0);

        return new Bomb(SIZE, rotation, angularVelocity, angularAcceleration, position, linearVelocity, linearAcceleration);
    }

    /**
     * Draws this Bomb.
     * @param g is the Graphics to paint with.
     */
    @Override
    void drawSprite(Graphics g) {
        double centerX = position.getX();
        double centerY = position.getY();

        g.drawRect(
                (int) (centerX),
                (int) (centerY),
                (int) (2 * collisionRadius),
                (int) (2 * collisionRadius)
        );
    }

    /**
     * Destroys this Bomb. Spawns several radially translating Missiles.
     * @return the spawned Missiles.
     */
    @Override
    AbstractSprite[] destroySprite() {
        AbstractSprite[] sprites = new AbstractSprite[0];

        if (age > maxAge) {
            sprites = new AbstractSprite[8];

            for (int i = 0; i < 8; i++) {
                sprites[i] = explode(this, i);
            }
        }

        return sprites;
    }

    /**
     * Spawns a new missile at this Bomb traveling in the direction indicated by the passed ordinal.
     * @param bomb the Bomb to spawn the missile at
     * @param ordinal indicates the direction that the missile should travel
     * @return the constructed Missile
     */
    private AbstractSprite explode(AbstractSprite bomb, int ordinal) {

        // Build the Rotation indicated by the ordinal
        double angle = ((double) ordinal) * (Math.PI / 4);

        // Set that rotation on the Bomb
        bomb.rotation = new AngularMovement(angle);

        return Missile.from(bomb);
    }
}
