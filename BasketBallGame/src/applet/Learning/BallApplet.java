/**
 * 
 */
package applet.Learning;

import java.applet.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;

import javax.imageio.ImageIO;

/**
 * This class is made to make a ball move in its own applet window
 * 
 * @author Zachary R. Jones
 *
 */
public class BallApplet extends Applet implements Runnable {
	/** Position of the circle */
	private double x_pos;
	/** Position of the circle y */
	private double y_pos;
	/** Speed of the circle */
	private double x_speed;
	/** Radius of the circle */
	private int radius;
	/** Applet Size x */
	private int appletsize_x;

	// declare two instance variables at the head of the program
	private Image dbImage;
	private Graphics dbg;

	// SOUND FILES
	// Instance variable AudioClip bounce
	private AudioClip bounce;

	// BACKIMAGE
	// Instance variable Image backImage
	private Image backImage;
	
	//Trying to get image to show up on applet
	MediaTracker tr;

	public void init() {
		// SOUND FILES
		// Load an audio file which is in the same directory as the class files
		// of the applet
		bounce = getAudioClip(getCodeBase(), "bounce.au");
		// to use this sound, bounce.play();
		// Only use .au files
		// Also 8 Bit, 8000 khz, Mono files

		// BACKIMAGE
		// load file Land.jpg
		backImage = getImage(getCodeBase(), "land.jpg");
		// Can also use .gif files
		// .gif files are good, because they are file transparent, so good for
		// overlay

		this.setSize(800, 800);

		x_pos = 100;
		y_pos = 100;
		radius = 20;
		x_speed = 0.001;
		appletsize_x = 512; // TODO: what is this number
	}

	/**
	 * Starts the applet
	 */
	public void start() {
		// define a new thread
		Thread th = new Thread(this);
		// start this thread
		th.start();
	}

	public void stop() {
	}

	public void destroy() {
	}

	/**
	 * As the program runs, this is where everything is calculated and changed
	 * within the applet
	 */
	public void run() {	
			try {
				// Stop thread for 20 milliseconds
				Thread.sleep(20);
			} catch (InterruptedException ex) {

			}

			// set Thread Priority to maximum value
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

			// lower ThreadPriority
			Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

		// run a long while (true) this mean sin our case "always"
		while (true) {
			// changing the x - position for the ball/circle
			x_pos = x_pos + x_speed;

			// THIS CODE: is used to make the ball bounce
			// Ball is bounced if its x - position reaches the right border of
			// the applet
			//if (x_pos > appletsize_x - radius) {
				// Change direction of ball movement
			//	x_speed = -0.01;
			//}
			// Ball is bounced if its x - position reaches the left border of
			// the applet
			//else if (x_pos < radius) {
			//	// Change direction of ball movement
			//	x_speed = +0.01;
			//}

			// THIS CODE: makes the ball beam to the other side
			// Test if ball has left applet area
			 if (x_pos > appletsize_x + radius) {
			 //Set a new x_pos value for the ball
			 x_pos = 200;
			 }
			 //repaint the applet
			repaint();
		}
	}

	public void paint(Graphics g) {

		// set color
		g.setColor(Color.red);

		// paint a filled colored circle
		g.fillOval((int)x_pos - radius, (int)y_pos - radius, 2 * radius, 2 * radius);

		// Drawing the image: g.drawImage(name, x-value, y-value, frame)
		tr = new MediaTracker(this);
		backImage = getImage(getCodeBase(), "\\lang.jpg");
		tr.addImage(backImage,0);
		g.drawImage(backImage, 0, 0, this);
	}

	/** Update - Method, implements double buffering */
	public void update(Graphics g) {
		dbImage = createImage(this.getSize().width, this.getSize().height);
		dbg = dbImage.getGraphics();

		// initialize buff
		if (dbImage == null) {

		}

		// clear screen in background
		dbg.setColor(getBackground());
		dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

		// draw elements in background
		dbg.setColor(getForeground());
		paint(dbg);

		// draw image on the screen
		g.drawImage(dbImage, 0, 0, this);
	}

	// -----------------------------MOUSE
	// EVENTS----------------------------------//

	/**
	 * This method is used to handle mouse down events
	 * 
	 * @param e
	 *            Event that happens
	 * @param x
	 *            X cord of mouse
	 * @param y
	 *            Y cord of mouse
	 * @return true, not sure why
	 */
	public boolean mouseDown(Event e, int x, int y) {
		// Change direction
		x_speed = -(x_speed);

		// DON'T FORGET (although not necessary here)!!
		return true;

		// NOTE: e.clickCount = gets the number of clicked performed
	}

	/**
	 * This method is used to hangle mouse up events
	 * 
	 * @param e
	 *            Event that happens
	 * @param x
	 *            X cord of mouse
	 * @param y
	 *            Y cord of mouse
	 * @return true, not sure why
	 */
	public boolean mouseUp(Event e, int x, int y) {
		// Some action

		// DON'T FORGET (although not necessary here)!!
		return true;

		// NOTE: e.clickCount = gets the number of clicked performed
	}

	/**
	 * This method handles eents that occur if mouse is moved over applet
	 * 
	 * @param e
	 *            Event that happens
	 * @param x
	 *            X cord of mouse
	 * @param y
	 *            Y cord of mouse
	 * @return true, not sure why
	 */
	public boolean mouseMove(Event e, int x, int y) {
		// Some action

		// DON'T FORGET (although not necessary here)!!
		return true;
	}

	/**
	 * This method handles events that occur if mouse is moved over the applet
	 * with pressed mouse button
	 * 
	 * @param e
	 *            Event that happens
	 * @param x
	 *            X cord of mouse
	 * @param y
	 *            Y cord of mouse
	 * @return true, not sure why
	 */
	public boolean mouseDrag(Event e, int x, int y) {
		// Some action

		// DON'T FORGET (although not necessary here)!!
		return true;
	}

	/**
	 * This method handles events that occur if mouse enters the applet
	 * 
	 * @param e
	 *            Event that happens
	 * @param x
	 *            X cord of mouse
	 * @param y
	 *            Y cord of mouse
	 * @return true, not sure why
	 */
	public boolean mouseEnter(Event e, int x, int y) {
		// Some action

		// DON'T FORGET (although not necessary here)!!
		return true;
	}

	/**
	 * This method handles events that occur if mouse leaves the applet
	 * 
	 * @param e
	 *            Event that happens
	 * @param x
	 *            X cord of mouse
	 * @param y
	 *            Y cord of mouse
	 * @return true, not sure why
	 */
	public boolean mouseExit(Event e, int x, int y) {
		// Some action

		// DON'T FORGET (although not necessary here)!!
		return true;
	}

	// ---------------------------------KEYBOARD
	// EVENTS----------------------------------//
	/**
	 * This method listens to events that occur if a key is pressed down
	 * 
	 * Every key has a value (ASCII). This value is given to the method with the
	 * help of the "key" variable. Space bar has a value of 32. If you want your
	 * applet to listen to Space bar pressed down, you only have to test, if the
	 * value of "key" is 32 (details later). Sometimes you don't know which
	 * value a certain key has. Then you can print out this value to the
	 * standard output by writing this line in your keyDown(...)
	 * 
	 * @param e
	 *            Event that happens
	 * @param key
	 *            Is the ASCII code of key entered
	 * @return not sure what this is
	 */
	public boolean keyDown(Event e, int key) {

		/*
		 * Additionally the method prints out the ASCII - value if an other key
		 * is pressed. This is not necessary but a possibility for you to test
		 * which value a key has.
		 */
		System.out.println("Charakter: " + (char) key + " Integer Value: "
				+ key);

		// user presses left cursor key
		if (key == Event.LEFT) {
			// changing x - speed so that ball moves to the left side(x_speed
			// negative)
			x_speed = -0.001;
		}

		// user presses right cursor key
		else if (key == Event.RIGHT) {

			// changing x - speed so that ball moves to the right side(x_speed
			// positive)
			x_speed = 0.001;
		}

		// user presses space bar (value = 32!)
		else if (key == 32) {

			// Stop ball (x_speed = 0)
			x_speed = 0;
		}
		// Any other key that is pressed
		else {

		}

		// DON'T FORGET (although it has no meaning here)
		return true;

		// ---------------------------NOT FOR BALL-----------------------
		// Will printout the key that you press down
		// System.out.println("Character:" + (char)key + "Integer Value: " key);

		// CURSOR KEYS
		// Event.LEFT, Event.RIGHT, Event.UP, Event.DOWN

		// DON'T FORGET (although not necessary here)!!
		// return true;
	}

	/**
	 * This method reacts to key up events, and can use just like key down
	 * 
	 * @param e
	 *            Event that happens
	 * @param key
	 *            Is the ASCII code of key entered
	 * @return not sure what this is
	 */
	public boolean keyUp(Event e, int key) {
		// Will print out the key that went up
		System.out.println("Character:" + (char) key + "Integer Value: " + key);

		// CURSOR KEYS
		// Event.LEFT, Event.RIGHT, Event.UP, Event.DOWN

		// DON'T FORGET (although not necessary here)!!
		return true;
	}
}

/**
 * Lessons Thread - a thread is a piece of program that is able to run parallel
 * to other parts of the program (multithreading - implemented with class
 * Thread, interface runable and method run() -Important Methods --
 * Thread.stat(); starts a thread -- Thread.stop(); stops a thread --
 * Thread.sleep(time in milliseconds): stops thread for a certain amount of time
 * 
 * ----------------------------------------------------------------- When you
 * make an applet, you see that the circle will flicker.
 * 
 * This is because every time the repair method is called, the applet is cleared
 * and rewritten.
 * 
 * To fix this we can do 1 of 3 things 1) We don't clear the screen at al 2) WE
 * clear the screen only where something is changing 3) We use double buffering
 * 
 * 1) Would mean that a solid line would go with the ball (layer on layer on
 * layer on ...)
 * 
 * public void update(Graphics g) { paint(g); }
 * 
 * 2) Good for a snake game
 * 
 * 3) Double buffering i) Generates a new offscreen-image by using createImage
 * and stores this image in a instance variable( = generate an empty image) ii)
 * CAll get Graphcis to get graphic ontext for this image iii) Draw everything
 * (including to clear the screen completely) on the offscreen image (= draw
 * image in the background) iv) When finished, copy this image over the existing
 * image on the screen (= draw image in foreground
 * 
 * so... i) generate an empty image ii) draw image on the background iii) draw
 * image on the foreground
 * 
 * The only disadvantage of the double buffering is, that it produces a large
 * amount of data and every image is drawn two times (offscreen and when copying
 * to the screen). But in most cases and on a fast computer this is much better
 * than wasting time on finding an other solution!
 * 
 * 
 */
