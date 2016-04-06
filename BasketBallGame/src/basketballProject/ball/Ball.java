package basketballProject.ball;

import java.util.Scanner;

/*************************************************************************
 * Compilation: javac Ball.java Execution: java Ball Dependencies: StdDraw.java
 *
 * Implementation of a 2-d Ball moving in square with coordinates between -1 and
 * 1. Bounces off the walls upon collision.
 *
 *
 *************************************************************************/

public class Ball {

	// instance variables
	private double rx, ry; // position
	private double vx, vy; // velocity
	private double difficulty; // tells the position of the goal
	// This variable tells whether you won or not
	private boolean won;
	// private final double ax = 0.0020; // acceleration (movement reason)
	private final double ay = -0.0030; // acceleration (gravity) -0.0030 good
	private final double radius; // radius
	private final double VERTICAL_BOUND_FRICTION_COEFFICENT = 0.03;
	private final double HORIZONTAL_BOUND_FRICTION_COEFFICENT_BOUNCE = 0.003;
	private final double HORIZONTAL_BOUND_FRICTION_COEFFICENT = 0.001;
	private static final double BOUNDS_OF_BALL = 5.0;
	private static final double BOUNDARY_CORRECTION = 0.07;

	// constructor
	/**
	 * Constructor of the Ball Makes a ball at the position (0,0) Velocity in
	 * the x and y are also made
	 * 
	 * Note: From th3e testing that I did on this class I found that the correct
	 * variables for the basketball game are: Radius = 0.05 Velocity = 0.045
	 * 
	 * ADDITIONS Acceration Kinematics equations v = vo + at v^2 = vo^2 + 2ax
	 * (THIS ONE) x = vot + 1/2at^2
	 */
	public Ball() {
		rx = -0.0;
		ry = 0.0;
		vx = 0.040;// - Math.random() * 0.03;
		vy = -0.050;// - Math.random() * 0.03;
		radius = 0.15; // + Math.random() * 0.05;
	}

	public Ball(double v, double angle, double difficulty, double x, double y) {
		rx = x;// -1 * (BOUNDS_OF_BALL - 1);
		ry = y;// -1 * (BOUNDS_OF_BALL - 1);
		vx = v * Math.cos((angle * Math.PI) / 180);
		vy = v * Math.sin((angle * Math.PI) / 180);
		this.difficulty = difficulty;
		radius = 0.30;

	}

	// Getter method for won and vx
	public boolean getWon() {
		return won;
	}

	public double getVx() {
		return vx;
	}

	public double getVy() {
		return vy;
	}

	// bounce of vertical wall by reflecting x-velocity
	private void bounceOffVerticalWall() {
		// The regular one for elastic conditions
		// vx = -vx;

		// The one so that it slows down when it hits a wall
		if (vx < 0) {
			vx = (-1 * vx) - (HORIZONTAL_BOUND_FRICTION_COEFFICENT_BOUNCE);
		} else if (vx > 0) {
			vx = (-1 * vx) + (HORIZONTAL_BOUND_FRICTION_COEFFICENT_BOUNCE);
		}
	}

	// Slows down the ball when it hits the ground
	private void bounceFriction() {
		if (vx <= HORIZONTAL_BOUND_FRICTION_COEFFICENT_BOUNCE
				&& vx > -1 * HORIZONTAL_BOUND_FRICTION_COEFFICENT_BOUNCE) {
			vx = 0;
		} else {
			if (vx < 0) {
				vx += HORIZONTAL_BOUND_FRICTION_COEFFICENT_BOUNCE;
			} else {
				vx -= HORIZONTAL_BOUND_FRICTION_COEFFICENT_BOUNCE;
			}
		}

	}

	// Set up a boundary so that if the ball hits the goal then
	// it will bounce
	private boolean goalBoundary(double difficulty) {
		// This boolean is to say if you won or not
		boolean won = false;
		// This is to see if it hits the goal or not

		// Case 4: hits the top of the vertical bar
		// The point is (difficulty, 1)
		if (rx + vx + radius > difficulty - BOUNDARY_CORRECTION
				&& rx + vx + radius < difficulty + BOUNDARY_CORRECTION) {
			if (ry - vy - radius < 1.5 && ry - vy - radius >= 0.5) {
				if (vy > 0) {
					vy = (-1 * vy) + (VERTICAL_BOUND_FRICTION_COEFFICENT);
				} else if (vy <= 0) {
					vy = -vy;
				}
			}
		}

		//TODO: problem when you put in 3, 0.4, 40
		//TODO: when the ball is going down, the vertial barrier does not work
		// Case 1: hits the first line (vertical)
		// The vertical line is (difficulty, 0) to (difficulty, 1)
		if (rx + vx + radius > difficulty - (BOUNDARY_CORRECTION)
				&& rx + vx + radius < difficulty + (BOUNDARY_CORRECTION)) {
			if (vy <= 0) { // If the ball is going down have to worry about
							// bottom hitting
				if (ry - vy - radius < 1 && ry - vy - radius >= -0.5) {
					if (vx < 0) {
						vx = (-1 * vx)
								- (HORIZONTAL_BOUND_FRICTION_COEFFICENT_BOUNCE);
					} else if (vx > 0) {
						vx = (-1 * vx)
								+ (HORIZONTAL_BOUND_FRICTION_COEFFICENT_BOUNCE);
					}
				}
			} else if (vy > 0) { // If the ball is going down has to worry bout
									// top hitting
				if (ry + vy + radius < 1 && ry + vy + radius >= -0.5) {
					if (vx < 0) {
						vx = (-1 * vx)
								- (HORIZONTAL_BOUND_FRICTION_COEFFICENT_BOUNCE);
					} else if (vx > 0) {
						vx = (-1 * vx)
								+ (HORIZONTAL_BOUND_FRICTION_COEFFICENT_BOUNCE);
					}
				}
			}
			won = false;
		}

		// Case 2: hits the second line (horizontal)
		// The horizontal line is (difficulty, 0) to (5,0)
		if (ry + vy + radius > -0.1 && ry + vy + radius <= 0.1) {
			if (rx + vx + radius < 5 + BOUNDARY_CORRECTION
					&& rx + vx + radius >= difficulty - BOUNDARY_CORRECTION) {
				if (vy <= 0) {
					vy = 0;
					vx = 0;
					won = true;
				} else if (vy > 0) {
					vy = (-1 * vy) + (VERTICAL_BOUND_FRICTION_COEFFICENT);
					won = false;
				}
				// TODO: Need to put in the window that says that you won
			}
		}
		/**
		 * if (rx + vx + radius < 5.2 && rx + vx + radius >= difficulty) { if
		 * (ry - vy - radius < 1.01 && ry - vy - radius >= 0.99) { // boundary
		 * // of // the // point if (vy <= 0) { vy = 0; vx = 0; won = true; }
		 * else if (vy > 0) { vy = (-1 * vy) +
		 * (VERTICAL_BOUND_FRICTION_COEFFICENT); won = false; } } won = false; }
		 */

		// Case 3: hits the intersect of the two lines (point)
		// The point is (difficulty, 0)
		if (rx + vx + radius == difficulty) {
			if (ry + vy + radius == 0) {
				// combination of both case one and case 2
				// Case 1
				if (rx + vx + radius > difficulty
						&& rx + vx + radius < difficulty + 0.45) {
					if (vy < 0) { // If the ball is going down have to worry
									// about bottom hitting
						if (ry - vy - radius < 1 && ry - vy - radius >= 0) { // TODO:
																				// this
																				// may
																				// need
																				// fine
																				// tuning
							if (vx < 0) {
								vx = (-1 * vx)
										- (HORIZONTAL_BOUND_FRICTION_COEFFICENT_BOUNCE);
							} else if (vx > 0) {
								vx = (-1 * vx)
										+ (HORIZONTAL_BOUND_FRICTION_COEFFICENT_BOUNCE);
							}
						}
					} else if (vy > 0) { // If the ball is going down has to
											// worry bout top hitting
						if (ry + vy + radius < 1 && ry + vy + radius >= 0) {
							if (vx < 0) {
								vx = (-1 * vx)
										- (HORIZONTAL_BOUND_FRICTION_COEFFICENT_BOUNCE);
							} else if (vx > 0) {
								vx = (-1 * vx)
										+ (HORIZONTAL_BOUND_FRICTION_COEFFICENT_BOUNCE);
							}
						}
					}
					won = false;
				}

				// Case 2
				if (rx + vx + radius < 5.2 && rx + vx + radius >= difficulty) {
					if (ry - vy - radius < 1.01 && ry - vy - radius >= 0.99) { // boundary
																				// of
																				// the
																				// point
						if (vy <= 0) {
							vy = 0;
							vx = 0;
							won = true;
						} else if (vy > 0) {
							vy = (-1 * vy)
									+ (VERTICAL_BOUND_FRICTION_COEFFICENT);
							won = false;
						}
					}
					won = false;
				}
			}
		}

		return won;
	}

	// bounce of horizontal wall by reflecting y-velocity
	private void bounceOffHorizontalWall() {
		// The regular one for elastic conditions
		// vy = -vy;

		// The one so that is slows down when it hits a wall
		if (vy < 0) {
			vy = (-1 * vy) - (ay + VERTICAL_BOUND_FRICTION_COEFFICENT);
			bounceFriction();
		} else if (vy > 0) {
			vy = (-1 * vy) + (ay + VERTICAL_BOUND_FRICTION_COEFFICENT);
			bounceFriction();
		}

		// The ball will just stop moving vertically
		if (vy < VERTICAL_BOUND_FRICTION_COEFFICENT
				&& vy > -1 * VERTICAL_BOUND_FRICTION_COEFFICENT) {
			vy = 0;
			bounceFriction();
		}
	}

	// move the ball one step
	public void move() {
		if (Math.abs(rx + vx) + radius >= BOUNDS_OF_BALL) { // position +
															// velocity > top
															// range
			bounceOffVerticalWall();
		}
		/**
		 * This is just for a top and bottom bound
		 *
		 * if (Math.abs(ry + vy) + radius > 5.0) { bounceOffHorizontalWall(); }
		 */

		// This is just for a bottom bound
		if (ry + vy + (-1 * radius) <= -1 * BOUNDS_OF_BALL) {
			;
			bounceOffHorizontalWall();
		}

		// This is to check the bounds for the goal
		won = goalBoundary(difficulty);

		// reset the position
		rx = rx + vx;
		ry = ry + vy;

		// reset the velocity
		if (vy != 0) {
			vy = vy + ay;
		}
		// If the ball is on the ground, friction
		if (ry < -1 * 4.5) { // 4.84 is the actual bound of the ball
			if (vx < 0) {
				vx += HORIZONTAL_BOUND_FRICTION_COEFFICENT;
			} else if (vx > 0) {
				vx -= HORIZONTAL_BOUND_FRICTION_COEFFICENT;
			}
		}
		// If the balls horizontal velocity is really small, just stop the ball
		if (vx < 0.001 && vx > -0.001) {
			vx = 0;
		}

	}

	// draw the ball
	public void draw() {
		StdDraw.filledCircle(rx, ry, radius);
	}

	// test client
	public static void main(String[] args) {
		System.out.println("BASKETBALL GAME\n");
		System.out.println("The difficult of this game goes as such:\n");
		System.out
				.println("2 = easiest\n3 = normal\n4 = hard\n5 = impossible\n");
		Scanner console = new Scanner(System.in);
		System.out.print("Difficult: ");
		double difficulty = console.nextDouble();
		System.out.print("What speed would you like for the ball: ");
		double v = console.nextDouble();
		System.out.print("What angle would you like for the ball: ");
		double angle = console.nextDouble();
		console.close();
		System.out.println("Answer Sheet");
		System.out.println("2 = 0.2, 45");
		System.out.println("3 = 0.25, 60");
		System.out.println("4");

		// create and initialize two balls
		Ball basketball = new Ball(v, angle, difficulty, -1
				* (BOUNDS_OF_BALL - 1), -1 * (BOUNDS_OF_BALL - 1));

		// animate them
		StdDraw.setXscale(-1 * BOUNDS_OF_BALL, BOUNDS_OF_BALL); // Can change
																// these two
		StdDraw.setYscale(-1 * BOUNDS_OF_BALL, BOUNDS_OF_BALL); // but will zoom
																// out with
																// higher number
		boolean question = true;
		while (question) {
			if (basketball.getWon()) {
				StdDraw.clear(StdDraw.BLUE);
				StdDraw.picture(0, 0, "naruto.jpg", 10, 10);
				StdDraw.show();
				question = false;
			} else if (basketball.getVx() == 0 && basketball.getVy() == 0) {
				StdDraw.clear(StdDraw.ORANGE);
				StdDraw.picture(0, 0, "narutoLost.jpg", 10, 10);
				StdDraw.show();
				question = false;
			} else {
				StdDraw.clear(StdDraw.DARK_GRAY); // Color of the background
													// //TODO:
				// pic
				// of a stat
				StdDraw.setPenColor(StdDraw.BLUE); // Color of the basketball
													// //TODO: pic of a
													// basketball
				// Call draw a picture of a basketball
				// StdDraw.picture(BOUNDS, 0, "basketBall.jpg", 0.60, 0.60);

				StdDraw.line(difficulty, 0, difficulty, 1);
				StdDraw.line(difficulty, 0, 5, 0);
				basketball.move();
				basketball.draw();
				StdDraw.show(1); // Animation speed (higher number = slower
									// speed)
			}
		}

	}
}