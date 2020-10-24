import java.awt.Color;
import java.awt.Graphics;

public class GameObj_TeleportBlock extends GameObj{
	public static final int SIZE = 40;
    //public static final int INIT_POS_X = 130;
    //public static final int INIT_POS_Y = 130;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    public static final int GRAVITY = 0; 
    
    //unique qualities of teleport
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


    public GameObj_TeleportBlock(int courtWidth, int courtHeight, int Px, int Py, int width, int height, Color color) {
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
		//parameters insufficient so attack method not used
	}
	
	public void teleport(GameObj Player, int initialPx, int initialPy) {
		Player.setPx(initialPx);
		Player.setPy(initialPy);
	}
}
