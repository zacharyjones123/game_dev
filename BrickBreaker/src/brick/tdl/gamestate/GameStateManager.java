package brick.tdl.gamestate;

import java.awt.Graphics2D;
import java.util.Stack;

import brick.tdl.gamestates.BrickBreakerLoader;

public class GameStateManager {
	
	public static Stack<GameState> states;

	public GameStateManager() {
		states = new Stack<GameState>();
		states.push(new BrickBreakerLoader(this));
		
	}
	
	public void tick (double deltaTime) {
		states.peek().tick(deltaTime);
		
	}
	
	public void render(Graphics2D g) {
		states.peek().render(g);
		
	}

	public void init() {
		states.peek().init();
		
	}

}
