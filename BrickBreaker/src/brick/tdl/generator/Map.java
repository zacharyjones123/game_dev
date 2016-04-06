package brick.tdl.generator;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import my.project.gop.main.Vector2F;
import my.project.gop.main.loadImageFrom;
import brick.tdl.MoveableObjects.Player;
import brick.tdl.generator.Block.BlockType;
import brick.tdl.main.Main;

public class Map {
	
	TileManager tiles = new TileManager();
	Player player = new Player();
	
	public Map() {
		
	}
	
	public void init() {
		player.init();
		
		BufferedImage map = null;
		
		try {
		map = loadImageFrom.LoadImageFrom(Main.class, "map.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(int x = 0; x < 100; x++) { //width of map
			for(int y = 0; y < 100; y++) { //length of map
				
				int col = map.getRGB(x, y);			
				switch(col & 0xFFFFFF) {
				case 0x505050: //GRAY
					tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.STONE_1));
					break;
				case 0x404040: //GRAY
					tiles.blocks.add(new Block(new Vector2F(x*48, y*48), BlockType.WALL_1).isSolid(true));
					break;
				}
			}
		}
		
	}
	
	public void tick(double deltaTime) {
		tiles.tick(deltaTime);
		player.tick(deltaTime);
	}
	
	public void render(Graphics2D g) {
		tiles.render(g);
		player.render(g);
	}

}
