import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameObj_Spike extends GameObj{
    public static final int SIZE = 40;
    //public static final int INIT_POS_X = 130;
    //public static final int INIT_POS_Y = 130;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    public static final int GRAVITY = 0; 
    
    //unique qualities of spike
    private boolean harmful = false;
    private Color color;
    
    //unique getters
    public boolean getHarmful() {
    	return this.harmful;
    }
    public Color getColor() {
    	return this.color;
    }
    
    // unique setters
    public void setHarmful(boolean harmful) {
    	this.harmful = harmful;
    }
    public void setColor(Color color) {
    	this.color = color;
    }

    public GameObj_Spike(int courtWidth, int courtHeight, int Px, int Py, int width, int height, Color color) {
        super(INIT_VEL_X, INIT_VEL_Y, Px, Py, width, height, courtWidth, courtHeight, GRAVITY);

        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
    	g.fillRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
    }

	@Override
	public void attack(Graphics g) {
		this.harmful = !this.harmful;
		if (harmful) {
			this.color = Color.RED;
		}
		else {
			this.color = Color.CYAN;
		}
	}
}
