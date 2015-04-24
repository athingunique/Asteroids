package csc171.project03.model;

/*
 * TimerCallback.java
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
 * Callback to let the Timer Thread notify the Controller of click events.
 */
public interface TimerCallback {
    void onTick();
}
