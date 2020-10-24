
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameObj_Block extends GameObj {

    
	public static final String IMG_FILE = "files/grass_block_stretched.png";
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    public static final int GRAVITY = 0; 

    private static BufferedImage img; 
    


    public GameObj_Block(int courtWidth, int courtHeight, int px, int py, int blockWidth, int blockHeight) {
        super(INIT_VEL_X, INIT_VEL_Y, px, py, blockWidth, blockHeight, courtWidth, courtHeight, GRAVITY);
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
		// the basic block does not attack 
	}
	
}
