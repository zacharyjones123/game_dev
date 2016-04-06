package brick.tdl.generator;

import java.awt.Graphics2D;
import java.util.ArrayList;

import my.project.gop.main.Vector2F;
import brick.tdl.MoveableObjects.Player;

public class TileManager {
	
	public static ArrayList<Block> blocks = new ArrayList<Block>();

	public TileManager() {
		
	}
	
	public void tick(double deltaTime) {
		for(Block block : blocks) {
			block.tick(deltaTime);
			
			if (Player.render.intersects(block)) {
				block.setAlive(true);
			} else {
				block.setAlive(false);
			}
			
			//block.pos.add(new Vector2F(1,0)); //makes the blocks move
		}
	}
	
	public void render(Graphics2D g) {
		for(Block block : blocks) {
			block.render(g);
		}
	}

}
