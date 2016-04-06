/**
 * 
 */
package applet.Learning;

import java.applet.*;
import java.awt.*;

/**
 * This is the beginning of my journey of learning
 * how to use applets and implement then in order to 
 * make a game, preferribly the BasketBall Game
 * 
 * @author Zachary R. Jones
 *
 */
//Inherit the applet class from the class Applet
public class FirstApplet extends Applet {
	// Now you should implement the following methods 

	// init - method is called the first time you enter the HTML site with the applet
	public void init() {... }

	// start - method is called every time you enter the HTML - site with the applet
	public void start() {... }

	// stop - method is called if you leave the site with the applet
	public void stop() {... }

	// destroy method is called if you leave the page finally (e. g. closing browser)
	public void destroy {... }

	/** paint - method allows you to paint into your applet. This method is called e.g. if you move your browser window or if you call repaint() */
	public void paint (Graphics g) { }
}

//TODO: In order to add to HTML document
/**
 * <html>
 * <body>
 * <p><applet code ="FirstApplet.class" width=700 height=400>
 * </applet></p>
 * </body>
 * </html>
 * 
 */