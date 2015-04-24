package csc171.project03.sprites;

/*
 * Asteroid.java
 * Version 1.0
 * Copyright Evan Baker
 * Course: CSC 171 SPRING 2015
 * Assignment: PROJECT 03
 * Author: Evan Baker
 * Lab Session: TR 0940-1055
 * Lab TA: Stephen Cohen
 * Last Revised: 4/19/15
 */

import csc171.project03.model.*;

import java.awt.*;
import java.util.Random;

/**
 * This class represents an Asteroid sprite and has a Factory to construct them randomly.
 */

public class Asteroid extends AbstractSprite {

    /**
     * Creates a new Asteroid.
     * @param collisionRadius the collision radius.
     * @param rotation the inital angular position.
     * @param angularVelocity the initial angular velocity.
     * @param angularAcceleration the initial angular acceleration.
     * @param position the initial rectilinear position.
     * @param linearVelocity the initial rectilinear velocity.
     * @param linearAcceleration the initial rectilinear acceleration.
     */
    private Asteroid(double collisionRadius, Movement rotation, Movement angularVelocity, Movement angularAcceleration, Movement position, Movement linearVelocity, Movement linearAcceleration) {
        super(collisionRadius, rotation, angularVelocity, angularAcceleration, position, linearVelocity, linearAcceleration);
    }

    /**
     * Asteroid Factory. Randomly spawns an Asteroid.
     * @param canvasDimensions the bounding Dimensions of the Canvas.
     * @param speedRange the speed range for this Asteroid.
     * @return a new, fully qualified Asteroid.
     */
    public static Asteroid spawn(Dimension canvasDimensions, double speedRange) {
        int collisionRadius = getSize();

        Movement rotation = new AngularMovement(0);
        Movement angularVelocity = new AngularMovement(0);
        Movement angularAcceleration = new AngularMovement(0);

        Movement position = getNewAsteroidPosition(canvasDimensions, collisionRadius);
        Movement linearVelocity = getNewAsteroidVelocity(position, canvasDimensions, speedRange);
        Movement linearAcceleration = new RectilinearMovement(0, 0);

        return new Asteroid(collisionRadius, rotation, angularVelocity, angularAcceleration, position, linearVelocity, linearAcceleration);
    }

    /**
     * @return the size of this Asteroid. One of {10, 20, 30, 40}.
     */
    private static int getSize() {
        return (1 + new Random().nextInt(4)) * 10;
    }

    /**
     * Makes a random Asteroid position on the perimeter of the passed Dimension with the passed size.
     * @param canvasDimensions the Dimension on which to place the Asteroid.
     * @param asteroidSize the size of the Asteroid to make.
     * @return the Asteroid position.
     */
    private static Movement getNewAsteroidPosition(Dimension canvasDimensions, int asteroidSize) {
        double width = canvasDimensions.getWidth();
        double height = canvasDimensions.getHeight();

        double randPosOnPerim = (2d * width + 2d * height) * Math.random();

        // This wraps the perim position around the top, right, bottom, left and gets the coordinates of the position
        double x;
        double y;

        if (randPosOnPerim - width < 0) {
            // TOP
            x = randPosOnPerim;
            y = 0 - (2 * asteroidSize);
        } else if (randPosOnPerim - (width + height) < 0) {
            // RIGHT
            x = width + asteroidSize;
            y = randPosOnPerim - width;
        } else if (randPosOnPerim - (2d * width + height) < 0) {
            // BOTTOM
            x = randPosOnPerim - (width + height);
            y = height + asteroidSize;
        } else {
            // LEFT
            x = 0 - (2 * asteroidSize);
            y = randPosOnPerim - (2d * width + height);
        }

        return new RectilinearMovement(x, y);
    }

    /**
     * Takes a position and bounding Dimension and creates a Velocity RectilinearMovement pointing inwards with a
     * random speed.
     * @param position the perimeter position.
     * @param canvasDimensions is the perimeter definition.
     * @param speedRange is the max speed possible.
     * @return the internally directed RectilinearMovement (Velocity).
     */
    private static Movement getNewAsteroidVelocity(Movement position, Dimension canvasDimensions, double speedRange) {
        double posX = position.getX();
        double posY = position.getY();

        double boundX = canvasDimensions.getWidth();
        double boundY = canvasDimensions.getHeight();

        int velX = 1;
        int velY = 1;

        // Determine which side of the bounds the perimeter position is on, and make the linearVelocity point inwards.
        if (posX > boundX / 2) {
            // Closer to right side.
            velX = -1;
        }

        if (posY > boundY / 2) {
            // Closer to bottom side.
            velY = -1;
        }

        // Get the components of the linearVelocity vector.
        double xComponent =  velX * AsteroidsMath.randRange((speedRange / 2), speedRange);
        double yComponent =  velY * AsteroidsMath.randRange((speedRange / 2), speedRange);

        return new RectilinearMovement(xComponent, yComponent);
    }

    /**
     * Draws this Asteroid.
     * @param g the Graphics to paint with.
     */
    @Override
    void drawSprite(Graphics g) {
        double centerX = position.getX();
        double centerY = position.getY();

        g.drawOval(
                (int) (centerX),
                (int) (centerY),
                (int) (2 * collisionRadius),
                (int) (2 * collisionRadius)
        );
    }

    /**
     * Asteroid specific destroy logic.
     * Also increments the Score in the GameKeeper.
     * @return either two smaller asteroids or nothing.
     */
    @Override
    public AbstractSprite[] destroySprite() {
        GameKeeper.addScore();

        if (collisionRadius > 10) {

            int splits = 2;

            AbstractSprite[] sprites = new AbstractSprite[splits];

            for (int i = 0; i < splits; i++) {
                sprites[i] = split(this);
            }

            return sprites;

        } else {
            return null;
        }
    }

    /**
     * Takes an Asteroid and returns a next smaller asteroid with a similar direction at a nearby position.
     * @param asteroid the asteroid to split.
     * @return the new child asteroid.
     */
    private static AbstractSprite split(Asteroid asteroid) {

        // Old position
        double x0 = asteroid.position.getX(); // The old position
        double y0 = asteroid.position.getY(); // The old position

        // Fuzz the position
        double x1 = ((10 - (Math.random() * 20)) + x0);
        double y1 = ((10 - (Math.random() * 20)) + y0);

        // New position
        Movement newPosition = new RectilinearMovement(x1, y1);

        // Old velocities
        double vx0 = asteroid.linearVelocity.getX(); // The old unit vector x component of the linearVelocity
        double vy0 = asteroid.linearVelocity.getY(); // The old unit vector y component of the linearVelocity

        // Get some randomized new speeds
        double vx1 = 1 + (Math.random() * (vx0));
        double vy1 = 1 + (Math.random() * (vy0));

        // New velocity
        Movement newVelocity = new RectilinearMovement(vx1, vy1);

        // New Asteroid
        return new Asteroid(
                (int) asteroid.collisionRadius - 10,
                asteroid.rotation,
                asteroid.angularVelocity,
                asteroid.angularAcceleration,
                newPosition,
                newVelocity,
                asteroid.linearAcceleration
        );
    }
}
