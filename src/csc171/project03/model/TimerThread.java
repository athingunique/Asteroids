package csc171.project03.model;

/*
 * TimerThread.java
 * Version 1.0
 * Copyright Evan Baker
 * Course: CSC 171 SPRING 2015
 * Assignment: LAB 01
 * Author: Evan Baker
 * Lab Session: TR 0940-1055
 * Lab TA: Stephen Cohen
 * Last Revised: 4/19/15
 */

import java.lang.ref.WeakReference;

/**
 * A Timer Thread. Sleeps for the specified amount of time, then calls back through the TimerCallback to notify the
 * Controller about the tick event.
 */

public class TimerThread implements Runnable {

    /**
     * The refresh rate (in frames per second).
     */
    private final long fps;

    /**
     * A weak reference to the TimerCallback try and prevent memory leaks.
     */
    private final WeakReference<TimerCallback> callback;


    /**
     * Creates a new TimerThread.
     * @param timerCallback the callback to trigger on Timer events.
     * @param fps the Timer refresh rate.
     */
    public TimerThread(TimerCallback timerCallback, long fps) {
        this.callback = new WeakReference<>(timerCallback);
        this.fps = fps;
    }

    /**
     * Creates and start a new TimerThread (as a Daemon).
     */
    public void start() {
        Thread timerThread = new Thread(this);
        timerThread.setDaemon(true);
        timerThread.start();
    }

    /**
     * The threaded work.
     */
    @Override
    public void run() {
        boolean runThread = true;
        do {
            try {
                // Sleep for the duration of the frame delay
                Thread.sleep(1000 / fps);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Get a hard reference to the TimerCallback
            TimerCallback hardTimerCallback = callback.get();

            // Let the thread die if the TimerCallback dne (shouldn't ever happen...)
            if (hardTimerCallback != null) {
                // callback exists, tick it
                hardTimerCallback.onTick();
            } else {
                // Lost reference to callback, thread may be orphaned, let it die
                runThread = false;
            }

        } while (runThread);
    }
}
