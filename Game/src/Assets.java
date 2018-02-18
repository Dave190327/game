

import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Assets {
	Game test=new Game();
	private static int width=79,height=79;
	public static BufferedImage[] tilesTemp=new BufferedImage[256];
	public static BufferedImage[] tiles=new BufferedImage[256];
	public static BufferedImage[] cards=new BufferedImage[256];
	public static BufferedImage[] grass=new BufferedImage[256];
	public static BufferedImage background,blackSmith;
	public static void init(){
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/Cards/Spritesheet.PNG"));
		int z=1;
		for(int y=0;y<3;y++) {
			for(int x=0;x<6;x++){
				tilesTemp[z]=sheet.crop((width*x)+x,(height*y)+y, width, height);
				z++;
			}
		}
		tiles=tilesTemp;
		tiles[1]=tilesTemp[2];
		tiles[2]=tilesTemp[3];
		tiles[3]=tilesTemp[4];
		sheet = new SpriteSheet(ImageLoader.loadImage("/Cards/cards.png"));
		z=1;width = 71;height = 95;
		for(int y=0;y<4;y++){
			for(int x=0;x<14;x++){
				cards[z]=sheet.crop((width*x)+x,(height*y)+y, width, height);
				z++;
			}
		}
		background = ImageLoader.loadImage("/Cards/background.jpg");
		blackSmith = ImageLoader.loadImage("/Cards/blackSmith.jpg");
	}
	
}