package csc171.project03.sprites;

import csc171.project03.model.*;

import java.awt.*;


/*
 * AbstractSprite.java
 * Version 1.0
 * Copyright Evan Baker
 * Course: CSC 171 SPRING 2015
 * Assignment: PROJECT 03
 * Author: Evan Baker
 * Lab Session: TR 0940-1055
 * Lab TA: Stephen Cohen
 * Last Revised: 4/19/15
 */

/**
 * This class defines some default behavior for a sprite that is then inherited in to some specific children.
 * Good use of package private in the fields here.
 */
public abstract class AbstractSprite {

    /**
     * The age of the Sprite, in millis.
     */
    long age = 0;

    /**
     * The collision radius of the Sprite.
     */
    double collisionRadius;

    /**
     * The angular position of the Sprite.
     */
    Movement rotation;

    /**
     * The angular velocity of the Sprite.
     */
    Movement angularVelocity;

    /**
     * The angular acceleration of the Sprite.
     */
    Movement angularAcceleration;

    /**
     * The rectilinear position of the Sprite.
     */
    Movement position;

    /**
     * The rectilinear velocity of the Sprite.
     */
    Movement linearVelocity;

    /**
     * The rectilinear acceleration of the Sprite.
     */
    Movement linearAcceleration;

    /**
     * Creates a new Sprite.
     * @param collisionRadius the collision radius.
     * @param rotation the inital angular position.
     * @param angularVelocity the initial angular velocity.
     * @param angularAcceleration the initial angular acceleration.
     * @param position the initial rectilinear position.
     * @param linearVelocity the initial rectilinear velocity.
     * @param linearAcceleration the initial rectilinear acceleration.
     */
    public AbstractSprite(
            double collisionRadius,
            Movement rotation,
            Movement angularVelocity,
            Movement angularAcceleration,
            Movement position,
            Movement linearVelocity,
            Movement linearAcceleration) {

        this.age = 0;
        this.collisionRadius = collisionRadius;
        this.rotation = rotation;
        this.angularVelocity = angularVelocity;
        this.angularAcceleration = angularAcceleration;
        this.position = position;
        this.linearVelocity = linearVelocity;
        this.linearAcceleration = linearAcceleration;
    }

    /**
     * @return the age of the Sprite, in millis.
     */
    public long getAge() {
        return this.age;
    }

    /**
     * Updates all of the Movement fields of the Sprite.
     * @param timeStep the timeStep to update the Movements over.
     */
    public void updateMovements(long timeStep) {
        age += timeStep;
        angularVelocity.nextDelta(rotation, timeStep);
        angularAcceleration.nextDelta(angularVelocity, timeStep);

        linearVelocity.nextDelta(position, timeStep);
        linearAcceleration.nextDelta(linearVelocity, timeStep);
    }

    /**
     * Retards the Movements. By damping, the Movements are all limited at some maximum rate. Also brings the Sprite to
     * rest when it's not actively being driven.
     */
    public void dampMovements() {
        angularVelocity.damp();
        angularAcceleration.damp();

        linearVelocity.damp();
        linearAcceleration.damp();
    }

    /**
     * Sets the angular acceleration of the Sprite.
     * @param angularAcceleration the Movement to set.
     */
    public void setAngularAcceleration(Movement angularAcceleration) {
        this.angularAcceleration = angularAcceleration;
    }

    /**
     * Increases the linear acceleration of the Sprite by a constant amount.
     */
    public void increaseAcceleration() {
        double accelMag = linearAcceleration.getMagnitude();
        setLinearAcceleration(RectilinearMovement.composeVector(rotation.getA(), accelMag + 20));
    }

    /**
     * Sets the linear acceleration of the Sprite.
     * @param linearAcceleration the Movement to set.
     */
    public void setLinearAcceleration(Movement linearAcceleration) {
        this.linearAcceleration = linearAcceleration;
    }

    /**
     * Increases the angular acceleration of the Sprite in the clockwise direction by a constant amount.
     */
    public void rotateRight() {
        double currentW = angularAcceleration.getA();
        setAngularAcceleration(new AngularMovement(currentW + 2));
    }

    /**
     * Increases the angular acceleration of the Sprite in the counterclockwise direction by a constant amount.
     */
    public void rotateLeft() {
        double currentW = angularAcceleration.getA();
        setAngularAcceleration(new AngularMovement(currentW - 2));
    }

    /**
     * Checks if the Sprite is within the passed Dimensions.
     * @param canvasDimensions the Dimensions to check
     * @return whether the Sprite is within the Dimensions
     */
    public boolean isInbounds(Dimension canvasDimensions) {

        double x = position.getX();
        double y = position.getY();

        double width = canvasDimensions.getWidth();
        double height = canvasDimensions.getHeight();

        boolean outOfBounds =
                x > width + collisionRadius
        ||      x < 0 - (2 * collisionRadius)
        ||      y > height + collisionRadius
        ||      y < 0 - (2 * collisionRadius);

        return !outOfBounds;
    }

    /**
     * If this Sprite is out of the bounds of the passed Dimension, teleports the Sprite to the other side of the
     * canvas (left to/from right and top to/from bottom).
     * @param canvasDimensions the Dimensions to wrap around.
     */
    public void wrap(Dimension canvasDimensions) {
        double x = position.getX();
        double y = position.getY();

        double width = canvasDimensions.getWidth();
        double height = canvasDimensions.getHeight();

        if (x > width + collisionRadius) {
            position.setX(0);
        };
        if (x < 0 - (2 * collisionRadius)) {
            position.setX(width);
        };
        if (y > height + collisionRadius) {
            position.setY(0);
        };
        if (y < 0 - (2 * collisionRadius)) {
            position.setY(height);
        };
    }

    /**
     * Returns if this Sprite has collided with the passed Sprite.
     * @param sprite is the Sprite to check collision with.
     * @return whether the Sprites have collided.
     */
    public boolean isCollidedWith(AbstractSprite sprite) {

        double x = position.getX() + collisionRadius;
        double y = position.getY() + collisionRadius;

        Movement com = new RectilinearMovement(x, y);

        double spriteX = sprite.position.getX() + sprite.collisionRadius;
        double spriteY = sprite.position.getY() + sprite.collisionRadius;

        Movement spriteCom = new RectilinearMovement(spriteX, spriteY);

        double separation = AsteroidsMath.getSeparation(com, spriteCom);
        double minSeparation = collisionRadius + sprite.collisionRadius;

        return separation < minSeparation;
    }

    /**
     * Calls the child Sprite's #drawSprite() method.
     * @param g is the Graphics to paint with.
     */
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        drawSprite(g);
    }

    /**
     * Draws this Sprite.
     * @param g the Graphics to paint with.
     */
    abstract void drawSprite(Graphics g);

    /**
     * Calls the child Sprite's #destroySprite() method.
     * @return an array of child AbstractSprites
     */
    public AbstractSprite[] destroy() {
        return destroySprite();
    }

    /**
     * Destroys this Sprite. Returns 0 to n Sprites that result from this one being destroyed.
     * @return an array of child AbstractSprites
     */
    abstract AbstractSprite[] destroySprite();
}
