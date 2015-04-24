package csc171.project03test.model;

/*
 * TimerThreadTest.java
 * Version 1.0
 * Copyright Evan Baker
 * Course: CSC 171 SPRING 2015
 * Assignment: PROJECT 03
 * Author: Evan Baker
 * Lab Session: TR 0940-1055
 * Lab TA: Stephen Cohen
 * Last Revised: 4/23/15
 */

import csc171.project03.model.TimerCallback;
import csc171.project03.model.TimerThread;

public class TimerThreadTest implements TimerCallback {
    public static void main(String[] args) {
        new TimerThreadTest().testTimerThread();
    }

    void testTimerThread() {
        System.out.println("Testing timer thread, will tick once per second for 5 seconds, then die.");
        new TimerThread(this, 1).start();

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTick() {
        System.out.println("Timer thread ticked.");
    }
}
