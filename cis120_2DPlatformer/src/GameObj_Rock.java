import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameObj_Rock extends GameObj {
	public static final String IMG_FILE = "files/block_obsidian.png";
	public static final int SIZE = 20;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    public static final int GRAVITY = -1; 

    private static BufferedImage img;


    /**
    * Note that, because we don't need to do anything special when constructing a Square, we simply
    * use the superclass constructor called with the correct parameters.
    */
    public GameObj_Rock(int courtWidth, int courtHeight, int px, int py ) {
        super(INIT_VEL_X, INIT_VEL_Y, px, py, SIZE, SIZE, courtWidth, courtHeight, GRAVITY);

        
        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
    }

	@Override
	public void attack(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}
