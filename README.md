# Asteroids

Evan Baker
CSC171
LAB TR 0940-1055
TA Stephen Cohen
Project 3

Assignment description: In this project, we practiced the everything that we have learned this semester including GUI, design patterns, threads, etc.


## TO BUILD (commandline)
In the root dir, run:
$ ./build.sh 
or the Windows equivalent.

Examine the contents of this build script to see specific package building implementation.

A runscript has also been provided

## TO RUN (commandline)
In the root dir (after building), run:
$ ./run.sh 
or the Windows equivalent.

## TO RUN (jar)
Run the provided Asteroids.jar!

## TO TEST (commandline) 
In the root dir (after building), run:
$ ./test.sh
or the Windows equivalent.


The runscript runs the Controller class.
The .jar also runs the Controller class. The .jar is packaged with the splash image, so it the splash will only be shown when running from the .jar.
The testscript runs a TestAll class.


## Class definitions
My initial class map is included as classMap.jpg

## Fundamental change
I added Bombs to the game. Bombs are dropped by pressing down arrow key. They have a fuse of 3 seconds, after which they explode and send missiles out radially. They also will explode if an asteroid collides with them.

## Testing
The testing spec says to write tests for my non GUI classes. This leaves my model classes: Movements, AsteroidsMath, TimerThread, GameKeeper, and KeyPress. A test class is provided for each of these that tests them insofar as they are statically testable, and prints pass/fails to the commandline.


## BONUS
It's ASTEROIDS! The complexity of this should be worth plenty.
Full JavaDoc in the doc/ folder.
Build/run/test scripts.
Self contained jar.

