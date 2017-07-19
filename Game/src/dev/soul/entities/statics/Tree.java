package dev.soul.entities.statics;

import java.awt.Graphics;

import dev.soul.Handler;
import dev.soul.gfx.Assets;
import dev.soul.items.Item;
import dev.soul.tiles.Tile;


public class Tree extends StaticEntity {

	public Tree(Handler handler, float x, float y) {
		super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT * 2);
		
		bounds.x = 10;
		bounds.y = (int) (height / 1.5f);
		bounds.width = width - 20;
		bounds.height = (int) (height - height / 1.5f);
	}

	@Override
	public void tick() {
		
	}
	
	@Override
	public void die(){
		handler.getWorld().getItemManager().addItem(Item.woodItem.createNew((int) x, (int) y));
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.tree, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
	}

}