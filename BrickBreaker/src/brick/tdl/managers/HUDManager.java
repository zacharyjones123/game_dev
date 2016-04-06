package brick.tdl.managers;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import my.project.gop.main.Light;
import my.project.gop.main.Vector2F;
import my.project.gop.main.loadImageFrom;
import brick.tdl.MoveableObjects.Player;
import brick.tdl.main.Main;

public class HUDManager {
	
	private Player player;
	private BufferedImage lightmap = new BufferedImage(100*32, 100*32, BufferedImage.TYPE_INT_ARGB);
	private ArrayList<Light> lights = new ArrayList<Light>();
	private Vector2F lightm = new Vector2F();
	private BufferedImage light;

	public HUDManager(Player player) {
		this.player = player;
		addLights();
		//light = loadImageFrom.LoadImageFrom(Main.class, "Light.png"); 1)
	}
	
	private void addLights() {
		//lights.add(new Light((int)(Main.width / 2.0 - player.getPos().xpos / 2), (int)(Main.height / 2 - player.getPos().ypos) / 2, 200, 255));
	}
	
	public void updateLights() {
		Graphics2D g = null;
		if(g == null) {
			g = (Graphics2D) lightmap.getGraphics();
		}
		g.setColor(new Color(0, 0, 0, 255)); //Black Screen
		g.fillRect(0, 0, lightmap.getWidth(), lightmap.getHeight());
		g.setComposite(AlphaComposite.DstOut); //Use lowercase, not DSTOUT
		
		for (Light light : lights) {
			light.render(g);
			//light.lightpos.xpos += 5; //can move the lights
		}
		g.dispose();
	}
	
	public void render(Graphics2D g) {
		//updateLights();
		
		g.drawImage(lightmap, (int)lightm.getWorldLocation().xpos, (int)lightm.getWorldLocation().ypos, null);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.width, Main.height / 6);
		g.fillRect(0, 700, Main.width, Main.height / 6);
		g.setColor(Color.WHITE);
		//g.drawImage(light, 0, 0, Main.width, Main.height, null); 2)
		
	}

}
