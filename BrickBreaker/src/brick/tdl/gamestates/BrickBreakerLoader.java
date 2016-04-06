package brick.tdl.gamestates;

import java.awt.Graphics2D;

import my.project.gop.main.SpriteSheet;
import my.project.gop.main.loadImageFrom;
import brick.tdl.gamestate.GameState;
import brick.tdl.gamestate.GameStateManager;
import brick.tdl.generator.Map;
import brick.tdl.main.Main;

//This is where the game is always made
public class BrickBreakerLoader extends GameState {

	Map map;
	public BrickBreakerLoader(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		map = new Map();
		map.init();
	}

	@Override
	public void tick(double deltaTime) {
		map.tick(deltaTime);
	}

	@Override
	public void render(Graphics2D g) {
		map.render(g);
	}

}
