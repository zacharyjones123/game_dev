package brick.tdl.main;

import java.awt.Point;

import brick.tdl.generator.Block;
import brick.tdl.generator.TileManager;

public class Check {
	
	public static boolean CollisionPlayerBlock(Point p1, Point p2) {
		for (Block block : TileManager.blocks) { //All of the blocks on the screen
			if (block.isSolid()) { //If the block is solid
				if (block.contains(p1) || block.contains(p2)) {
					return true; //COLLIDING
				}
			}
			
		}
		
		return false;
	}

}
