/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;




/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact with one another. Take
 * time to understand how the timer interacts with the different methods and how it repaints the GUI
 * on every tick().
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {
	
	
    // the state of the game logic
    private Square square; // the Black Square, keyboard control
    private Circle snitch; // the Golden Snitch, bounces
    private GameObj_Block upperBlock; //upper impassable block
    private GameObj_Block lowerBlock; // lower impassable block 
    private GameObj_Lava lava; //lava that falls from the ceiling
    private GameObj_Rock rock1; // rock that falls from the ceiling
    private GameObj_Spike spike1; //spike that flashes red if harmful, cyan if not harmful
    private GameObj_TeleportBlock teleportBlock1; //block that teleports you back to the start LOL
    private int score; // score based on time elapsed
    private TreeMap<String, Integer> scoreMap = new TreeMap<String, Integer>();
    private GameObj_PointBlock [][] blockArray = new GameObj_PointBlock[2][3];
    
    public int getScore() {
    	return this.score;
    }
    
    //visibility switch back and forth 
    private Timer lavaTimer; 
    
    private List<GameObj_TeleportBlock> tpList1 = new LinkedList<GameObj_TeleportBlock>(); 
    private List<GameObj_Rock> rockList1 = new LinkedList<GameObj_Rock>();
    private List<Timer> rockTimerList = new LinkedList<Timer>(); 


    public boolean playing = false; // whether the game is running 
    private JLabel status; // Current status text, i.e. "Running..."


    // Game constants
    public static final int COURT_WIDTH = 1150;
    public static final int COURT_HEIGHT = 400;
    public static final int SQUARE_VELOCITY_X = 10;
    public static final int SQUARE_VELOCITY_Y = 20;

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35;
    
    public Timer timer = new Timer(INTERVAL, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            tick();
            score++;
        }
    });
    
    public void rockGenerator(int timerInterval, int Px, int Py) {
    	Timer rocktimer = new Timer(timerInterval, new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                rock1 = new GameObj_Rock(COURT_WIDTH, COURT_HEIGHT, Px, Py); 
        		rockList1.add(rock1);
        	}
        }
    	);
    	this.rockTimerList.add(rocktimer); 
    }
    public void lavaGenerator(int timerInterval, int lavaHeight) {
    	this.lavaTimer = new Timer(timerInterval, new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		lava.attack(getGraphics());
        	}
        });
    	lava.setHeight(lavaHeight);
    }
    
    public void teleportGenerator(int Px, int Py) {
        teleportBlock1 = new GameObj_TeleportBlock(COURT_WIDTH, COURT_HEIGHT, Px, Py, 30, 30, Color.MAGENTA);
        tpList1.add(teleportBlock1); 
    }
    
    public Timer spikeTimer = new Timer(5000, new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    		spike1.attack(getGraphics());
    	}
    });
    
    public void playerSpawner(int Px, int Py) {
        square = new Square(COURT_WIDTH, COURT_HEIGHT, Px, Py, Color.BLACK);
    }
    
    public void popUpMessage (String args) {
    	JOptionPane.showMessageDialog(null, args);
    }
    
    
    //allow spacebar input for game or not???
    public void blockArrayPopulator() {
    	for (int i = 0; i < 2; i++) {
    		for (int j = 0; j < 3; j++) {
    			double randomInt = Math.random();
    	    	if (randomInt > 0.3) {
        			this.blockArray[i][j] = new GameObj_PointBlock(COURT_WIDTH, COURT_HEIGHT, (550 + 50*j), (240 - 25*i), 50, 25);
    	    	}
    	    	else {
        			this.blockArray[i][j] = null; 
    	    	}
    		}
    	}
    		
	}
    
    public void blockArrayDrawer(Graphics g) {
    	for (int i = 0; i < 2; i++) {
    		for (int j = 0; j < 3; j++) {
    	    	if (this.blockArray[i][j] == null) {
        			//do nothing
    	    	}
    	    	else {
        			this.blockArray[i][j].draw(g);
    	    	}
    		}
    	}
    }
    
    
    //testing methods
    
    public void printPlayerPos() {
    	System.out.print("x:" + square.getPx());
    	System.out.println(" y:" + square.getPy());
    }
    
    public int getPlayerPy() {
    	return square.getPy();
    }
    
    public int getPlayerPx() {
    	return square.getPx(); 
    }
    
    public void printLavaHeight() {
    	System.out.println(lava.getHeight());
    }
    
    
    
    
    //JLabel yourScore, JLabel topScores
    public GameCourt(JLabel status) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        
        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the square to move as long as an arrow key is pressed, by
        // changing the square's velocity accordingly. (The tick method below actually moves the
        // square.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    square.setVx(-SQUARE_VELOCITY_X);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    square.setVx(SQUARE_VELOCITY_X);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    square.setVy(-SQUARE_VELOCITY_Y);
                } else if (e.getKeyCode() == KeyEvent.VK_T) {
                	square.setPx(square.INIT_POS_X);
                	square.setPy(square.INIT_POS_Y);
                }
            }

            public void keyReleased(KeyEvent e) {
            	if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            		square.setVx(0);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            		square.setVx(0);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    square.setVy(0);
                }
            }
        });

        this.status = status;
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        snitch = new Circle(COURT_WIDTH, COURT_HEIGHT, Color.YELLOW);
        //do all values have to be encapsulated, even the integers below? 
        upperBlock = new GameObj_Block(COURT_WIDTH, COURT_HEIGHT, 0, 0, 1150, 100);
        lowerBlock = new GameObj_Block(COURT_WIDTH, COURT_HEIGHT, 0, 320, 1150, 100);
        lava = new GameObj_Lava(COURT_WIDTH, COURT_HEIGHT); 
        spike1 = new GameObj_Spike(COURT_WIDTH, COURT_HEIGHT, 400, 100, 70, 250, Color.RED);
        rockGenerator(1000, 300, 0);
        rockGenerator(200, 600, 0);
        rockGenerator(1000, 700, 0);
        rockGenerator(1000, 800, 0);
        lavaGenerator(20000, 40);
        //creating multiple timers????
        teleportGenerator(200, 295);
        teleportGenerator(400, 295);
        teleportGenerator(700, 295);
        blockArrayPopulator(); 
        playerSpawner(0, 250);
        
        
        //reset score 
        this.score = 0; 
        
        //restart all timers
        timer.restart();
        for(Timer timer: this.rockTimerList) {
        	timer.restart();
        }
        lavaTimer.restart(); 
        spikeTimer.restart(); 
                
        //back to playing
        
        playing = true;
        status.setText("Running...");

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }
    
    public static Comparator<Map.Entry<String, Integer>> scoreComparer = 
	new Comparator<Map.Entry<String, Integer>>() {
    	public int compare(Map.Entry<String, Integer> score1, Map.Entry<String, Integer> score2) {
    		return (score1.getValue().compareTo(score2.getValue()));
    	}
    };
    
    public void winGame() {
    	System.out.println(Game.getUsername());
    	timer.stop();
    	lavaTimer.stop();
    	spikeTimer.stop();
    	this.rockList1.clear();
    	File scorefile = new File("files/scores.txt"); 
    	try {
			BufferedReader buffReader = new BufferedReader(new FileReader(scorefile));
			String lBoardLine;
	    	while ((lBoardLine = buffReader.readLine()) != null) {
	    		if (lBoardLine.contains(":")) {
    			lBoardLine.trim();
	    		String name = lBoardLine.substring(0, lBoardLine.indexOf(':'));
	    		String score = lBoardLine.substring(lBoardLine.indexOf(':') + 1);
	    		int numScore = Integer.parseInt(score);
	    		this.scoreMap.put(name, numScore);
	    		}
	    		else {
	    			System.out.println("Error Message: No colon in leaderboard");
	    		}
	    		
    		}
	    	if (scoreMap.containsKey(Game.getUsername())) {
    			if (scoreMap.get(Game.getUsername()) > this.score) {
    	    		this.scoreMap.put(Game.getUsername(), this.score);
    			}
    		}
    		else {
	    		this.scoreMap.put(Game.getUsername(), this.score);
	    	}
	    	
	    	buffReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Internal Error:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		} catch (IllegalFormatException e) {
			System.out.println("Internal Error:" + e.getMessage()); 
		} catch (IllegalArgumentException e) {
    	    System.out.println("Internal Error:" + e.getMessage());
    	} 
    	
    	List<Map.Entry<String,Integer>> listOfScoreMap = new LinkedList<Map.Entry<String, Integer>>(this.scoreMap.entrySet());
    	Collections.sort(listOfScoreMap, scoreComparer);
    	
    	Iterator<Map.Entry<String,Integer>> ascIt = listOfScoreMap.iterator(); //ascending iterator 
    	int iteratorCounter = 0; //counter for the iterator
    	try {
			BufferedWriter buffWriter = new BufferedWriter(new FileWriter(scorefile));
			while (ascIt.hasNext() && iteratorCounter < 10) {
				Map.Entry<String,Integer> nextElement = ascIt.next();
				buffWriter.write(nextElement.getKey());
				buffWriter.write(":");
				System.out.println(nextElement.getValue());
	    		buffWriter.write(Integer.toString((nextElement.getValue())));
	    		buffWriter.newLine();
	    		iteratorCounter++;
	    	}
			buffWriter.close();
		} catch (FileNotFoundException e) {
			System.out.println("Internal Error:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		} catch (IllegalFormatException e) {
			System.out.println("Internal Error:" + e.getMessage()); 
		} catch (IllegalArgumentException e) {
    	    System.out.println("Internal Error:" + e.getMessage());
    	}
       	playing = false;
		status.setText("You won!");
		popUpMessage("Your score was " + Integer.toString(this.score));
    }
    
    public void endGame() {
    	timer.stop();
    	lavaTimer.stop();
    	spikeTimer.stop();
    	this.rockList1.clear();
    	playing = false;
		status.setText("You lose! " + "Score: " + Integer.toString(this.score));
    }
    
    /**
     * This method is called every time the timer defined in the constructor triggers.
     */
    void tick() {
        if (playing) {
            status.setText("Running... " + "Score: " + Integer.toString(this.score));

            // advance the square and snitch in their current direction.
            square.move();

            // in-game physics (gravity, passability)
            //square (player)
            if (!square.intersects(lowerBlock)) {
            	if (square.willIntersect(lowerBlock)) {
            		square.setPy(300);
            		square.setVy(0);
            	}
            	else {
            		square.setVy(square.getVy() - square.getGravity());
            		square.setPy(Math.min(Math.max(square.getPy(), 0), 300));
            	}
            }
            
            if (square.willIntersect(lowerBlock)) {
            	square.setPy(300);
            }
            if (square.willIntersect(upperBlock)) {
            	square.setPy(100);
            }
            
            //point blocks
            
            for (int i = 0; i < 2; i++) {
        		for (int j = 0; j < 3; j++) {
        	    	if (!(this.blockArray[i][j] == null) && square.intersects(blockArray[i][j])){
            			this.score = this.score - 10;
            			this.blockArray[i][j] = null; 
        	    	}
        		}
        	}
            
            //rock (obstacle)
            
            for(GameObj_Rock rocks: this.rockList1) {
            	rocks.move();
            }
            
            if (!(this.rockList1.size() == 0)) {
            	if (!this.rockList1.get(0).hitWallBool()) {
                	this.rockList1.get(0).setVy(this.rockList1.get(0).getVy() - this.rockList1.get(0).getGravity());
            	}
            	else if (this.rockList1.get(0).hitWallBool()) {
            		rockList1.remove(this.rockList1.get(0));
            	}
            }
            
            
            // teleport block
            
            
            for(GameObj_TeleportBlock tpBlock: this.tpList1) {
            	if (square.intersects(tpBlock)) {
            		tpBlock.teleport(square, square.INIT_POS_X, square.INIT_POS_Y);
            	}
            }
            
            // check for the game end conditions

            
            if (square.yIntersects(lava)) {
                endGame();
            }
            if (square.intersects(spike1) && spike1.getHarmful()) {
            	endGame();
            }
            
            
            
            for(GameObj_Rock rocks: this.rockList1) {
            	if (square.intersects(rocks)) {
            		endGame(); 
            	}
            }

            if (square.intersects(snitch)) {
            	winGame(); 
            }
            

            // update the display
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        snitch.draw(g);
        upperBlock.draw(g);
        lowerBlock.draw(g);
        lava.draw(g);
        spike1.draw(g);
        for(GameObj_TeleportBlock tpBlock: this.tpList1) {
        	tpBlock.draw(g);
        }
        blockArrayDrawer(g); 
        for(GameObj_Rock rocks: this.rockList1) {
        	rocks.draw(g);
        }
        square.draw(g);

        
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}