import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameObj_Lava extends GameObj {
	//create buffered image to make it from in the directory 
	public static final String IMG_FILE = "files/block_lava.png";
    public static final int SIZE = 40;
    public static final int INIT_POS_X = 0;
    public static final int INIT_POS_Y = 0;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    public static final int GRAVITY = 0; 

    private static BufferedImage img;
    

    public GameObj_Lava(int courtWidth, int courtHeight) {
        super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE, courtWidth, courtHeight, GRAVITY);
        
        
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
        g.drawImage(img, this.getPx(), this.getPy(), GameCourt.COURT_WIDTH , this.getHeight(), null);
    }

	@Override
	public void attack(Graphics g) {
		this.setHeight(this.getHeight() + SIZE);
		//this.setPy(this.getPy() + SIZE);
	}
}
