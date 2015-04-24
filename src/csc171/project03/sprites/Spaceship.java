package csc171.project03.sprites;

import csc171.project03.model.*;

import java.awt.*;

/*
 * Spaceship.java
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
 * This is the user controlled spaceship. It's a singleton because only one should exist per game.
 */
public class Spaceship extends AbstractSprite {

    /**
     * The Spaceship spawn delay.
     */
    static final long RESPAWN_ANIM_TIME = 250;

    /**
     * The Spaceship spawn animation max size.
     */
    static final int RESPAWN_ANIM_MAX_DIAMETER = 100;

    /**
     * The Spaceship singleton.
     */
    static Spaceship spaceship = null;

    /**
     * The height of the triangle representing the Spaceship.
     */
    static final double SPRITE_HEIGHT = 20;

    /**
     * The width of the triangle representing the Spaceship.
     */
    static final double SPRITE_WIDTH = 8;

    /**
     * Creates a new Spaceship.
     * @param collisionRadius the collision radius.
     * @param rotation the inital angular position.
     * @param angularVelocity the initial angular velocity.
     * @param angularAcceleration the initial angular acceleration.
     * @param position the initial rectilinear position.
     * @param linearVelocity the initial rectilinear velocity.
     * @param linearAcceleration the initial rectilinear acceleration.
     */
    public Spaceship(double collisionRadius, Movement rotation, Movement angularVelocity, Movement angularAcceleration, Movement position, Movement linearVelocity, Movement linearAcceleration) {
        super(collisionRadius, rotation, angularVelocity, angularAcceleration, position, linearVelocity, linearAcceleration);
    }

    /**
     * Getter for the Spaceship singleton.
     * @return the Spaceship singleton.
     */
    public static Spaceship getSpaceship() {
        if (spaceship == null) {
            // All spaceships start in the middle of the canvas, pointing up with no linearVelocity
            spaceship = new Spaceship(10, null, null, null, null, null, null);
        }

        return spaceship;
    }

    /**
     * Spawn a new Spaceship on the Canvas.
     * Also resets the {@link GameKeeper#score}.
     * @param canvasDimensions the Canvas Dimensions.
     * @return the new SpaceShip.
     */
    public Spaceship spawn(Dimension canvasDimensions) {
        GameKeeper.resetScore();

        age = 0;

        this.position = new RectilinearMovement(canvasDimensions.getWidth() / 2, canvasDimensions.getHeight() / 2); // Position in middle of canvas
        this.linearVelocity = new RectilinearMovement(0, 0); // No translational speed
        this.linearAcceleration = new RectilinearMovement(0, 0); // No translational acceleration

        this.rotation = new AngularMovement((3 * Math.PI) / 2); // Pointing up
        this.angularVelocity = new AngularMovement(0); // No rotational velocity
        this.angularAcceleration = new AngularMovement(0); // No rotational acceleration

        return this;
    }

    /**
     * Draws this Spaceship.
     * @param g the Graphics to paint with.
     */
    @Override
    void drawSprite(Graphics g) {

        // If the age of this sprite is less than the respawn animation duration, drawn the respawn animation
        if (age < RESPAWN_ANIM_TIME) {
            double diameter = RESPAWN_ANIM_MAX_DIAMETER - ((((double) age + 1) / RESPAWN_ANIM_TIME) * ((double) RESPAWN_ANIM_MAX_DIAMETER));

            double x = position.getX() - diameter / 2;
            double y = position.getY() - diameter / 2;

            g.drawRect(
                    (int) x,
                    (int) y,
                    (int) diameter,
                    (int) diameter
            );

        } else {

            // The sprite should point in the rotation of it's linearVelocity, so the 3 points of the triangle need to be
            // calculated around the position and that linearVelocity.

            int[] triX = makeTriangleX();
            int[] triY = makeTriangleY();

            g.fillPolygon(triX, triY, 3);
        }
    }

    /**
     * Does the math to locate the x components of the vertices of the triangle representing the Spaceship.
     * @return the x components of the triangle.
     */
    private int[] makeTriangleX() {
        double angle = rotation.getA();

        int x0 = (int) (position.getX() + (SPRITE_HEIGHT * Math.cos(angle)));
        int x1 = (int) (position.getX() + (SPRITE_WIDTH * Math.cos(angle + (Math.PI / 2))));
        int x2 = (int) (position.getX() + (SPRITE_WIDTH * Math.cos(angle + ((3 * Math.PI) / 2))));

        return new int[] {x0, x1, x2};
    }

    /**
     * Does the math to locate the y components of the vertices of the triangle representing the Spaceship.
     * @return the y components of the triangle.
     */
    private int[] makeTriangleY() {
        double angle = rotation.getA();

        int y0 = (int) (position.getY() + (SPRITE_HEIGHT * Math.sin(angle)));
        int y1 = (int) (position.getY() + (SPRITE_WIDTH * Math.sin(angle + (Math.PI / 2))));
        int y2 = (int) (position.getY() + (SPRITE_WIDTH * Math.sin(angle + ((3 * Math.PI) / 2))));

        return new int[] {y0, y1, y2};
    }

    /**
     * Destroys this Spaceship.
     * @return null, indicating not to spawn any new Sprites because this one is destroyed.
     */
    @Override
    AbstractSprite[] destroySprite() {
        return null;
    }
}
