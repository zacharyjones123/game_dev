package brick.tdl.MoveableObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import my.project.gop.main.Vector2F;
import brick.tdl.gameloop.GameLoop;
import brick.tdl.main.Animator;
import brick.tdl.main.Assets;
import brick.tdl.main.Check;
import brick.tdl.main.Main;
import brick.tdl.managers.GUIManager;
import brick.tdl.managers.HUDManager;

public class Player implements KeyListener {

	Vector2F pos;
	private int width = 64*2; // player smaller then blocks (48)
	private int height = 64*2;
	private int scale = 2;
	private static boolean up, down, left, right;
	private float maxSpeed = 3 * 32F;

	private float speedUp = 0;
	private float speedDown = 0;
	private float speedLeft = 0;
	private float speedRight = 0;

	private float slowdown = 4.093F;

	private float fixDt = 1 / 60F; // timeStep
	
	
	/*
	 * Rendering
	 */
	private int renderDistanceW = 48;
	private int renderDistanceH = 20;
	public static Rectangle render;
	
	//TODO
	private int animationState = 0;
	
	
	/*
	 * 0 = up
	 * 1 = down
	 * 2 = right
	 * 3 = left
	 * 4 = idle
	 */
	
	private ArrayList<BufferedImage> listUp;
	Animator ani_up;
	private ArrayList<BufferedImage> listDown;
	Animator ani_down;
	private ArrayList<BufferedImage> listLeft;
	Animator ani_left;
	private ArrayList<BufferedImage> listRight;
	Animator ani_right;
	
	private ArrayList<BufferedImage> listIdle;
	Animator ani_idle;
	
	private HUDManager hudm;
	private GUIManager guim;
	
	

	public Player() {
		guim = new GUIManager();
		pos = new Vector2F(Main.width / 2 - width / 2, Main.height / 2 - height
				/ 2); // exactly in center
		hudm = new HUDManager(this);
	}

	public void init() {
		
		render = new Rectangle(
				(int) (pos.xpos - pos.getWorldLocation().xpos + pos.xpos - renderDistanceW * 32 / 2 + width / 2),
				(int) (pos.ypos - pos.getWorldLocation().ypos + pos.ypos - renderDistanceH * 32 / 2 + height / 2),
				renderDistanceW * 32,
				renderDistanceH * 32);
		
		listUp = new ArrayList<BufferedImage>();
		listDown = new ArrayList<BufferedImage>();
		listRight = new ArrayList<BufferedImage>();
		listLeft = new ArrayList<BufferedImage>();
		listIdle = new ArrayList<BufferedImage>();
		
		listUp.add(Assets.player.getTile(0,0, 16, 16));
		listUp.add(Assets.player.getTile(16, 0, 16, 16));
		
		listDown.add(Assets.player.getTile(0,16, 16, 16));
		listDown.add(Assets.player.getTile(16, 16, 16, 16));
		
		//DARO
		listRight.add(Assets.daro.getTile(192, 64, 64, 64));
		listRight.add(Assets.daro.getTile(320, 64, 64, 64));
		listRight.add(Assets.daro.getTile(256, 64, 64, 64));
		
		//INDIE DEVELOPER
		//listRight.add(Assets.player.getTile(32, 16, 16, 16));
		//listRight.add(Assets.player.getTile(48, 16, 16, 16));
		//listRight.add(Assets.player.getTile(64, 16, 16, 16));
		//listRight.add(Assets.player.getTile(80, 16, 16, 16));
		
		//DARO
		listLeft.add(Assets.daro.getTile(128, 192, 64, 64));
		listLeft.add(Assets.daro.getTile(256, 192, 64, 64));
		listLeft.add(Assets.daro.getTile(192, 192, 64, 64));
		
		//INDIE DEVELOPER
		//listLeft.add(Assets.player.getTile(32, 0, 16, 16));
		//listLeft.add(Assets.player.getTile(48, 0, 16, 16));
		//listLeft.add(Assets.player.getTile(64, 0, 16, 16));
		//listLeft.add(Assets.player.getTile(80, 0, 16, 16));
		 
		//DARO
		listIdle.add(Assets.daro.getTile(0, 128, 64, 64));
		listIdle.add(Assets.daro.getTile(192, 128, 64, 64));
		listIdle.add(Assets.daro.getTile(256, 128, 64, 64));
		listIdle.add(Assets.daro.getTile(64, 128, 64, 64));
		//INDIE DEVELOPER
		//listIdle.add(Assets.player.getTile(0, 32, 16, 16));
		//listIdle.add(Assets.player.getTile(16, 32, 16, 16));
		//listIdle.add(Assets.player.getTile(32, 32, 16, 16));
		//listIdle.add(Assets.player.getTile(48, 32, 16, 16));
		
		
		//UP
		ani_up = new Animator(listUp);
		ani_up.setSpeed(180); //180ms, 1000 is 1 sec.
		ani_up.play();
		
		//DOWN
		ani_down = new Animator(listDown);
		ani_down.setSpeed(180);
		ani_down.play();
		
		//RIGHT
		ani_right = new Animator(listRight);
		ani_right.setSpeed(180);
		ani_right.play();
		
		//LEFT
		ani_left = new Animator(listLeft);
		ani_left.setSpeed(180);
		ani_left.play();
		
		//IDLE
		ani_idle = new Animator(listIdle);
		ani_idle.setSpeed(180);
		ani_idle.play();
		

	}

	public void tick(double deltaTime) {
		
		render = new Rectangle(
				(int) (pos.xpos - pos.getWorldLocation().xpos + pos.xpos - renderDistanceW * 32 / 2 + width / 2),
				(int) (pos.ypos - pos.getWorldLocation().ypos + pos.ypos - renderDistanceH * 32 / 2 + height / 2),
				renderDistanceW * 32,
				renderDistanceH * 32);

		// 1 pixel per deltaTime
		float moveAmountu = (float) (speedUp * fixDt);
		float moveAmountd = (float) (speedDown * fixDt);
		float moveAmountl = (float) (speedLeft * fixDt);
		float moveAmountr = (float) (speedRight * fixDt);
		// kind of a glidish kind of movement
		//To make the make move
		if (up) {
			moveMapUp(moveAmountu);
			animationState = 0;
		} else {
			moveMapUpGlide(moveAmountu);
		}

		if (down) {
			moveMapDown(moveAmountd);
			animationState = 1;
		} else {
			moveMapDownGlide(moveAmountd);
		}
		
		if (right) {
			moveMapRight(moveAmountr);
			animationState = 2;
		} else {
			moveMapRightGlide(moveAmountr);
		}

		if (left) {
			moveMapLeft(moveAmountl);
			animationState = 3;
		} else {
			moveMapLeftGlide(moveAmountl);
		}
		
		if(!up && !down && !right && !left) {
			/** standing stil */
			animationState = 4;
		}
	}
	public void PlayerMoveCode(float speed) {
		if (up) {
			moveMapUp(speed);
		} else {
			moveMapUpGlide(speed);
		}

		if (down) {
			moveMapDown(speed);
		} else {
			moveMapDownGlide(speed);
		}

		if (left) {
			moveMapLeft(speed);
		} else {
			moveMapLeftGlide(speed);
		}

		if (right) {
			moveMapRight(speed);
		} else {
			moveMapRightGlide(speed);
		}
	}

	public void moveMapUp(float speed) {

		//the two points are
		// p1 = bottom right
		// p2 = bottom right, plus width of player
		if (!Check.CollisionPlayerBlock(

		new Point((int) (pos.xpos + GameLoop.map.xpos), // Up
				(int) (pos.ypos + GameLoop.map.ypos - speed)),
		new Point((int) (pos.xpos + GameLoop.map.xpos + width),
				(int) (pos.ypos + GameLoop.map.ypos - speed)))) {

			if (speedUp < maxSpeed) { // Speed up player
				speedUp += slowdown;
			} else {
				speedUp = maxSpeed;
			}
			GameLoop.map.ypos -= speed;
		} else {
			speedUp = 0;
		}

	}

	public void moveMapUpGlide(float speed) {

		if (!Check.CollisionPlayerBlock(

		new Point((int) (pos.xpos + GameLoop.map.xpos),
				(int) (pos.ypos + GameLoop.map.ypos - speed)),
		new Point( (int) (pos.xpos + GameLoop.map.xpos + width),
				(int) (pos.ypos + GameLoop.map.ypos - speed)))) {
			if (speedUp != 0) {
				speedUp -= slowdown; // Slow Down player

				if (speedUp < 0) { // Will eventually stop if keep slowing down
					speedUp = 0;
				}
			}
			GameLoop.map.ypos -= speed;

		} else {
			speedUp = 0;
		}

	}

	public void moveMapDown(float speed) {

		if (!Check.CollisionPlayerBlock(

		new Point((int) (pos.xpos + GameLoop.map.xpos), // Up
				(int) (pos.ypos + GameLoop.map.ypos + height + speed)),
				new Point((int) (pos.xpos + GameLoop.map.xpos + width),
						(int) (pos.ypos + GameLoop.map.ypos + height + speed)))) {

			if (speedDown < maxSpeed) {
				speedDown += slowdown;
			} else {
				speedDown = maxSpeed;
			}
			GameLoop.map.ypos += speed;
		} else {
			speedDown = 0;
		}

	}

	public void moveMapDownGlide(float speed) {

		if (!Check.CollisionPlayerBlock(

		new Point((int) (pos.xpos + GameLoop.map.xpos), // Up
				(int) (pos.ypos + GameLoop.map.ypos + height + speed)),
				new Point((int) (pos.xpos + GameLoop.map.xpos + width),
						(int) (pos.ypos + GameLoop.map.ypos + height + speed)))) {

			if (speedDown != 0) {
				speedDown -= slowdown; // Slow Down player

				if (speedDown < 0) { // Will eventually stop
					speedDown = 0;
				}
			}
			GameLoop.map.ypos += speed;
		} else {
			speedDown = 0;
		}

	}

	public void moveMapRight(float speed) {

		if (!Check.CollisionPlayerBlock(

		new Point((int) (pos.xpos + GameLoop.map.xpos + width + speed), // Up
				(int) (pos.ypos + GameLoop.map.ypos)), new Point(
				(int) (pos.xpos + GameLoop.map.xpos + width + speed),
				(int) (pos.ypos + GameLoop.map.ypos + height)))) {
			if (speedRight < maxSpeed) {
				speedRight += slowdown;
			} else {
				speedRight = maxSpeed;
			}
			GameLoop.map.xpos += speed;
		} else {
			speedRight = 0;
		}

	}

	public void moveMapRightGlide(float speed) {
		if (!Check.CollisionPlayerBlock(

		new Point((int) (pos.xpos + GameLoop.map.xpos + width + speed), // Up
				(int) (pos.ypos + GameLoop.map.ypos)), new Point(
				(int) (pos.xpos + GameLoop.map.xpos + width + speed),
				(int) (pos.ypos + GameLoop.map.ypos + height)))) {
			if (speedRight != 0) {
				speedRight -= slowdown; // Slow Down player

				if (speedRight < 0) { // Will eventually stop
					speedRight = 0;
				}
			}
			GameLoop.map.xpos += speed;
		} else {
			speedRight = 0;
		}

	}

	public void moveMapLeft(float speed) {

		if (!Check.CollisionPlayerBlock(

		new Point((int) (pos.xpos + GameLoop.map.xpos - speed), // Up
				(int) (pos.ypos + GameLoop.map.ypos + height)), new Point(
				(int) (pos.xpos + GameLoop.map.xpos - speed),
				(int) (pos.ypos + GameLoop.map.ypos)))) {
			if (speedLeft < maxSpeed) {
				speedLeft += slowdown;
			} else {
				speedLeft = maxSpeed;
			}
			GameLoop.map.xpos -= speed;
		} else {
			speedLeft = 0;
		}

	}

	public void moveMapLeftGlide(float speed) {

		if (!Check.CollisionPlayerBlock(

		new Point((int) (pos.xpos + GameLoop.map.xpos - speed), // Up
				(int) (pos.ypos + GameLoop.map.ypos + height)), new Point(
				(int) (pos.xpos + GameLoop.map.xpos - speed),
				(int) (pos.ypos + GameLoop.map.ypos)))) {

			if (speedLeft != 0) {
				speedLeft -= slowdown; // Slow Down player

				if (speedLeft < 0) { // Will eventually stop
					speedLeft = 0;
				}
			}
			GameLoop.map.xpos -= speed;
		} else {
			speedLeft = 0;
		}

	}

	public void render(Graphics2D g) {
		//g.fillRect((int) pos.xpos, (int) pos.ypos, width, height); //gives the white square for debugging
		
		//To make the movie look
		//g.clearRect(0, 0, Main.width, Main.height / 6); //Makes a black box given the parameters
		//g.clearRect(0, Main.height - Main.height / 6, Main.width, Main.height / 6);
		
		if (animationState == 0) {
			//UP
			g.drawImage(ani_up.sprite, (int)pos.xpos - width / 2, (int)pos.ypos - height, width * scale, height * scale, null);
			if(up) {
				ani_up.update(System.currentTimeMillis());
			}
		}
		if (animationState == 1) {
			//DOWN
			g.drawImage(ani_down.sprite, (int)pos.xpos - width / 2, (int)pos.ypos - height, width * scale, height * scale, null);
			if(down) {
				ani_down.update(System.currentTimeMillis());
			}
		}
		if (animationState == 2) {
			//RIGHT
			g.drawImage(ani_right.sprite, (int)pos.xpos - width / 2, (int)pos.ypos - height, width * scale, height * scale, null);
			if(right) {
				ani_right.update(System.currentTimeMillis());
			}
		}
		if (animationState == 3) {
			//LEFT
			g.drawImage(ani_left.sprite, (int)pos.xpos - width / 2, (int)pos.ypos - height, width * scale, height * scale, null);
			if(left) {
				ani_left.update(System.currentTimeMillis());
			}
		}
		if (animationState == 4) {
			//IDLE
			g.drawImage(ani_idle.sprite, (int)pos.xpos - width / 2, (int)pos.ypos - height, width * scale, height * scale, null);
				ani_idle.update(System.currentTimeMillis());
		}
		
		g.drawRect((int) pos.xpos - renderDistanceW * 32 / 2 + width / 2, (int) pos.ypos - renderDistanceH * 32 / 2 + height / 2, renderDistanceW * 32, renderDistanceH * 32);
		guim.render(g);
		hudm.render(g);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W) {
			up = true;
		}
		if (key == KeyEvent.VK_A) {
			left = true;
		}
		if (key == KeyEvent.VK_D) {
			right = true;
		}
		if (key == KeyEvent.VK_S) {
			down = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W) {
			up = false;
		}
		if (key == KeyEvent.VK_A) {
			left = false;
		}
		if (key == KeyEvent.VK_D) {
			right = false;
		}
		if (key == KeyEvent.VK_S) {
			down = false;
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	/**--------------------GETTERS-------------------*/
	
	public Vector2F getPos() {
		return pos;
	}
	
	public float getMaxSpeed() {
		return maxSpeed;
	}
	
	public float getSlowdown() {
		return slowdown;
	}

}
