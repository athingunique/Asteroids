package csc171.project03.controller;

/*
 * Controller.java
 * Version 1.0
 * Copyright Evan Baker
 * Course: CSC 171 SPRING 2015
 * Assignment: PROJECT 10
 * Author: Evan Baker
 * Lab Session: TR 0940-1055
 * Lab TA: Stephen Cohen
 * Last Revised: 4/19/15
 */

import csc171.project03.model.*;
import csc171.project03.sprites.*;
import csc171.project03.view.SpaceCanvas;
import csc171.project03.view.SpaceFrame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;

/**
 * The game controller class. Keeps track of all of the objects that exist in the game at any point and is the
 * communication layer between them and each other and the rest of the view.
 */
public class Controller implements TimerCallback, KeyEventCallback {

    /**
     * Refresh rate, in frames per second (Hz).
     */
    private final long FPS = 60;

    /**
     * Tracks whether the game has been started. Used to determine when to show the splash screen.
     */
    private boolean hasStarted = false;

    /**
     * A handle on the splash Image so that it doesn't have to be looked up on every redraw.
     */
    private Image splashImage = null;

    /**
     * Set that keeps tracked of the currently pressed keys.
     */
    private final Set<KeyPress.VALID_KEYS> keysPressed;

    /**
     * The Spaceship.
     */
    private final Spaceship mSpaceShip;

    /**
     * List of Asteroids.
     */
    private final List<AbstractSprite> mAsteroids;

    /**
     * List of Missiles.
     */
    private final List<AbstractSprite> mMissiles;

    /**
     * List of Bombs.
     */
    private final List<AbstractSprite> mBombs;

    /**
     * The Canvas that everything is drawn on.
     */
    private final SpaceCanvas mSpaceCanvas;

    /**
     * The single entry point to this application.
     * @param args commandline arguments.
     */
    public static void main(String[] args) {
        new Controller();
    }

    /**
     * Sets up and returns a new Controller
     */
    public Controller() {
        // Get the splash image from file
        try {
            InputStream is = getClass().getResourceAsStream("asteroid.png");
            if (is != null) {
                splashImage = ImageIO.read(is);
            }
        } catch (IOException e) {
            System.out.println("Splash image not found");
        }

        // Init the keys pressed list
        keysPressed = new HashSet<>();

        // Init the sprites/sprite collections
        mSpaceShip = Spaceship.getSpaceship();
        mAsteroids = new LinkedList<>();
        mMissiles = new LinkedList<>();
        mBombs = new LinkedList<>();

        // Init the display canvas
        mSpaceCanvas = new SpaceCanvas(this);

        // Put the canvas in a window and show it
        new SpaceFrame()
                .addCanvas(mSpaceCanvas)
                .display();

        // Use a Buffer to reduce flicker
        mSpaceCanvas.createBufferStrategy(2);

        // Make some mAsteroids
        for (int i = 0; i < 3; i++) {
            mAsteroids.add(Asteroid.spawn(mSpaceCanvas.getSize(), 100));
        }

        // Spawn the spaceship
        mSpaceShip.spawn(mSpaceCanvas.getSize());

        // Start drawing
        new TimerThread(this, FPS).start();
    }

    /**
     * This method is called when the timing thread ticks, triggering a frame repaint
     */
    @Override
    public void onTick() {
        GameKeeper.addRunTime(1000 / FPS);
        onKeysPressed();
        paintFrame();
    }

    /**
     * Key press handler. If a key is in the keysPressed Set, calls the action for that key press.
     */
    private void onKeysPressed() {
        if (keysPressed.contains(KeyPress.VALID_KEYS.UP)) {
            mSpaceShip.increaseAcceleration();
        }
        if (keysPressed.contains(KeyPress.VALID_KEYS.RIGHT)) {
            mSpaceShip.rotateRight();
        }
        if (keysPressed.contains(KeyPress.VALID_KEYS.LEFT)) {
            mSpaceShip.rotateLeft();
        }
        if (keysPressed.contains(KeyPress.VALID_KEYS.SPACE)) {
            if (GameKeeper.shouldSpawnMissile(mMissiles.size())) {
                mMissiles.add(Missile.from(mSpaceShip));
            }
        }
        if (keysPressed.contains(KeyPress.VALID_KEYS.DOWN)) {
            if (GameKeeper.shouldSpawnBomb(mBombs.size())) {
                mBombs.add(Bomb.from(mSpaceShip));
            }
        }
    }

    /**
     * This method does graphics set up, clean up, and delegates painting on the canvas to some helpers.
     */
    private void paintFrame() {
        // Get the Graphics from the Buffer.
        BufferStrategy bufferStrategy = mSpaceCanvas.getBufferStrategy();
        Graphics canvasGraphics = bufferStrategy.getDrawGraphics();

        // Get the Canvas dimensions for bounds checking.
        Dimension canvasDimensions = mSpaceCanvas.getSize();

        // Spawn extra asteroids if necessary.
        if (GameKeeper.shouldSpawnAsteroid(mAsteroids.size())) {
            spawnAsteroids(canvasDimensions, 100);
        }

        // Paint the background.
        paintBackground(canvasGraphics, canvasDimensions);

        // If the game has been started, paint the sprites.
        if (hasStarted) {
            // Paint the Sprites.
            paintSpaceship(canvasGraphics, canvasDimensions);
            paintAsteroids(canvasGraphics, canvasDimensions);
            paintBombs(canvasGraphics, canvasDimensions);
            paintMissiles(canvasGraphics, canvasDimensions);
        } else {
            // Paint the splash and instructions.
            paintSplash(canvasGraphics, canvasDimensions);
        }

        // Flush the Graphics.
        if (canvasGraphics != null) {
            canvasGraphics.dispose();
        }

        // Show, leveraging the Buffer.
        bufferStrategy.show();

        // Synchronizes the state of AWT. Ensures that the display is up to date.
        Toolkit.getDefaultToolkit().sync();
    }

    private void spawnAsteroids(Dimension canvasDimensions, double speedRange) {
        mAsteroids.add(Asteroid.spawn(canvasDimensions, speedRange));
    }

    /**
     * Paints the splash image and instructions.
     * @param canvasGraphics the graphics to paint with.
     * @param canvasDimensions the canvas dimensions.
     */
    private void paintSplash(Graphics canvasGraphics, Dimension canvasDimensions) {
        // Draws the Splash Image (if it has been loaded).
        if (splashImage != null) {
            canvasGraphics.drawImage(
                    splashImage,
                    ((int) canvasDimensions.getWidth() / 2) - (splashImage.getWidth(null) / 2),
                    ((int) canvasDimensions.getHeight() / 2) - (splashImage.getHeight(null) / 2),
                    null);
        }

        // Instruction texts.
        final String start = "Press any key to start!";
        final String up = " ^  ACCELERATE";
        final String lr = "< > ROTATE";
        final String down = " v  BOMB";
        final String space = " _  SHOOT";

        // Draw the instruction text.
        canvasGraphics.setColor(Color.WHITE);
        canvasGraphics.drawChars(start.toCharArray(), 0, start.length(), 20, (int) canvasDimensions.getHeight() - 100);
        canvasGraphics.drawChars(up.toCharArray(), 0, up.length(), 20, (int) canvasDimensions.getHeight() - 85);
        canvasGraphics.drawChars(lr.toCharArray(), 0, lr.length(), 20, (int) canvasDimensions.getHeight() - 70);
        canvasGraphics.drawChars(down.toCharArray(), 0, down.length(), 20, (int) canvasDimensions.getHeight() - 55);
        canvasGraphics.drawChars(space.toCharArray(), 0, space.length(), 20, (int) canvasDimensions.getHeight() - 40);
    }

    /**
     * Paints the background of the canvas black.
     * Also paints the score and elapsed time.
     * @param canvasGraphics the graphics to paint with.
     * @param canvasDimensions the canvas dimensions.
     */
    private void paintBackground(Graphics canvasGraphics, Dimension canvasDimensions) {
        // Fill the canvas with black.
        canvasGraphics.setColor(Color.BLACK);
        canvasGraphics.fillRect(0, 0, canvasDimensions.width, canvasDimensions.height);

        // Feedback texts.
        String score = "Score\t" + String.valueOf(GameKeeper.getScore());
        String time = "Time \t" + String.valueOf(((double) mSpaceShip.getAge() / 1000d));

        // Paint the feedback texts.
        canvasGraphics.setColor(Color.WHITE);
        canvasGraphics.drawChars(score.toCharArray(), 0, score.length(), 20, (int) canvasDimensions.getHeight() - 5);
        canvasGraphics.drawChars(time.toCharArray(), 0, time.length(), 20, (int) canvasDimensions.getHeight() - 20);
    }

    /**
     * Paints the Spaceship on the canvas..
     * @param canvasGraphics the graphics to paint with.
     * @param canvasDimensions the canvas dimensions.
     */
    private void paintSpaceship(Graphics canvasGraphics, Dimension canvasDimensions) {
        if (mSpaceShip != null) {
            if (mSpaceShip.isInbounds(canvasDimensions)) {

                // Call the movement update methods on the Sprite.
                mSpaceShip.dampMovements();
                mSpaceShip.updateMovements(1000 / FPS);

                // Draw the Sprite.
                mSpaceShip.draw(canvasGraphics);

            } else {

                // Wrap the Sprite around the canvas if it's out of bounds.
                mSpaceShip.wrap(canvasDimensions);
            }
        }
    }

    /**
     * Paints the Asteroids on the canvas..
     * @param canvasGraphics the graphics to paint with.
     * @param canvasDimensions the canvas dimensions.
     */
    private void paintAsteroids(Graphics canvasGraphics, Dimension canvasDimensions) {
        // Loop through all of the Asteroids in the List.
        for (int asteroidIndex = 0; asteroidIndex < mAsteroids.size(); asteroidIndex++) {
            AbstractSprite asteroid = mAsteroids.get(asteroidIndex);

            if (asteroid != null) {

                // Check that the asteroid is in bounds.
                if (asteroid.isInbounds(canvasDimensions)) {

                    // Check if the asteroid has collided with the spaceShip.
                    if (asteroid.isCollidedWith(mSpaceShip)) {
                        mSpaceShip.spawn(canvasDimensions);
                    }

                    // Check collisions with the missiles
                    for (int missileIndex = 0; missileIndex < mMissiles.size(); missileIndex++) {
                        AbstractSprite missile = mMissiles.get(missileIndex);

                        if (asteroid != null) {

                            if (asteroid.isCollidedWith(missile)) {
                                // It has collided, call the destroy method on this one.
                                AbstractSprite[] sprites = asteroid.destroy();

                                // If destroy returns children, add them to the collection to be spawned in on the next
                                // frame.
                                if (sprites != null && sprites.length > 0) {
                                    Collections.addAll(mAsteroids, sprites);
                                }

                                asteroid = null;
                                mAsteroids.remove(asteroidIndex);

                                mMissiles.remove(missileIndex);
                            }
                        }
                    }

                    // Check for collisions with the bombs.
                    for (AbstractSprite bomb : mBombs) {
                        if (asteroid != null) {

                            if (asteroid.isCollidedWith(bomb)) {
                                // If has collided, call the destroy method on this one.
                                AbstractSprite[] sprites = asteroid.destroy();

                                // If destroy returns children, add them to the collection to be spawned in on the next.
                                // frame
                                if (sprites != null && sprites.length > 0) {
                                    Collections.addAll(mAsteroids, sprites);
                                }

                                // Make the bomb explode an the next paint.
                                bomb.updateMovements(3000);

                                // Remove the asteroid from the Collection.
                                asteroid = null;
                                mAsteroids.remove(asteroidIndex);
                            }
                        }
                    }

                    // The asteroid hasn't been destroyed yet, so draw it.
                    if (asteroid != null) {
                        asteroid.updateMovements(1000 / FPS);
                        asteroid.draw(canvasGraphics);
                    }

                } else {

                    // Asteroid out of bounds, dereference it.
                    mAsteroids.remove(asteroidIndex);
                }
            }
        }
    }

    /**
     * Paints the Bombs on the canvas.
     * @param canvasGraphics the graphics to paint with.
     * @param canvasDimensions the canvas dimensions.
     */
    private void paintBombs(Graphics canvasGraphics, Dimension canvasDimensions) {
        // Loop through the List of Bombs
        for (int i = 0; i < mBombs.size(); i++) {
            AbstractSprite bomb = mBombs.get(i);

            if (bomb != null) {

                // Try to destroy the Bomb.
                AbstractSprite[] missiles = bomb.destroy();

                // If the bomb should be destroyed, there will be missiles.
                if (missiles.length > 0) {

                    // Add all the missile results to the Missiles collection.
                    Collections.addAll(mMissiles, missiles);

                    // Derefernce the Bomb.
                    mBombs.remove(i);

                } else {
                    // Bomb wasn't destroyed, tick it and redraw it.
                    bomb.updateMovements(1000 / FPS);
                    bomb.draw(canvasGraphics);

                }
            }
        }
    }

    /**
     * Paints the Missiles on the canvas.
     * @param canvasGraphics the graphics to paint with.
     * @param canvasDimensions the canvas dimensions.
     */
    private void paintMissiles(Graphics canvasGraphics, Dimension canvasDimensions) {
        // Loop through the List of Missiles.

        for (AbstractSprite missile : mMissiles) {

            if (missile != null) {

                // Check that the missile is in bounds.
                if (missile.isInbounds(canvasDimensions)) {

                    // Draw the Missile if it's in bounds.
                    missile.updateMovements(1000 / FPS);
                    missile.draw(canvasGraphics);

                } else {
                    // Wrap the Missile around the Canvas.
                    missile.wrap(canvasDimensions);

                }
            }
        }
    }

    /**
     * Key pressed callback. Adds the passed VALID_KEYS enum item to the keysPressed Set.
     * @param key the pressed key.
     */
    @Override
    public void onKeyPressed(KeyPress.VALID_KEYS key) {
        // Set has started to true.
        hasStarted = true;
        // Add the key that is pressed.
        keysPressed.add(key);
    }

    /**
     * Key released callback. Removes the VALID_KEYS enum item from the keysPressed Set.
     * @param key the released key.
     */
    @Override
    public void onKeyReleased(KeyPress.VALID_KEYS key) {
        // Remove the key this is released.
        keysPressed.remove(key);
    }
}
