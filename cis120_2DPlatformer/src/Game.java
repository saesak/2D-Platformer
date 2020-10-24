/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.IllegalFormatException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
	//variables
	
	private static String username; 
	
	//getters
	
	public static String getUsername() {
		return username; 
	}
	
	//helpers 
	public static boolean isAcceptedCharacter(int c) {
        if (Character.isLetter(c) || Character.isDigit(c)) {
            return true;
        }
        return false;
    }
	
	public static boolean checkWord(String s) {
        if (s == "") {
            return false;
        }
        else if (s == null) {
            return false;
        }
        else if (s.length() > 13 || s.length() == 0) {
        	return false;
        }
        char[] arrays = s.toCharArray();
        int arrLength = s.length();
        for (int i = 0; i < arrLength; i++) {
            if (!isAcceptedCharacter(arrays[i])) {
                return false;
            }
        }
        return true;
    }
	
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for local variables.

        // Top-level frame in which game components live
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
    	
    	//start frame 
    	
    	
    	
    	final JFrame startFrame = new JFrame("Starting Menu");
    	startFrame.setLocation(200, 400);
    	startFrame.setSize(1150, 400);
    	final JPanel startPanel = new JPanel();
    	startPanel.setBackground(Color.WHITE);
    	startFrame.setVisible(true);
    	startFrame.add(startPanel);
    	
    	//leaderboard frame
    	
    	final JFrame leaderboardFrame = new JFrame("Leaderboard");
    	leaderboardFrame.setLocation(200, 400);
    	leaderboardFrame.setSize(1150, 400);
    	final JPanel leaderboardPanel = new JPanel();
    	leaderboardPanel.setBackground(Color.WHITE);
        leaderboardFrame.setVisible(false);
        leaderboardFrame.add(leaderboardPanel); 
        String leaderboardLabelString1 = "<html><body>";
        String leaderboardLabelString2 = ""; 
        String leaderboardLabelString3 = "</body></html>";
        File scorefile = new File("files/scores.txt"); 
    	try {
			BufferedReader buffReader = new BufferedReader(new FileReader(scorefile));
			String lBoardLine;
			int loopCounter = 1;
	    	while ((lBoardLine = buffReader.readLine()) != null) {
	    		lBoardLine.trim();
	    		String name = lBoardLine.substring(0, lBoardLine.indexOf(':'));
	    		String score = lBoardLine.substring(lBoardLine.indexOf(':') + 1);
	    		leaderboardLabelString2 = leaderboardLabelString2 + Integer.toString(loopCounter) + "." + name + " " + score + "<br>";
	    		loopCounter++;
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
        JLabel leaderboardLabel = new JLabel(
        		leaderboardLabelString1 + leaderboardLabelString2 + leaderboardLabelString3
        		);
        leaderboardPanel.add(leaderboardLabel);
    	
        final JFrame frame = new JFrame("REALLY FUN GAME");
        frame.setLocation(200, 400);
        frame.setSize(1150, 400);
        //instructions frame
        
        final JFrame instructionsFrame = new JFrame("Instructions");
    	instructionsFrame.setLocation(200, 400);
    	instructionsFrame.setSize(1150, 400);
    	final JPanel instructionsPanel = new JPanel();
    	instructionsPanel.setBackground(Color.WHITE);
    	instructionsFrame.add(instructionsPanel); 
    	instructionsFrame.setVisible(false);
        
    	
    	// Status panel
        final JPanel statusPanel = new JPanel();
        frame.add(statusPanel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        statusPanel.add(status);
    	
        // Main playing area
        final GameCourt court = new GameCourt(status);
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel controlPanel = new JPanel();
        frame.add(controlPanel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we define it as an
        // anonymous inner class that is an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
            }
        });
        controlPanel.add(reset);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(false);

        
        // Buttons, Labels and Username Input
    	
        JTextField usernameTF = new JTextField(40);

        
        final JButton startGame = new JButton("Start");
        startGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	leaderboardFrame.setVisible(false);
            	startFrame.setVisible(false);
            	instructionsFrame.setVisible(false);
            	frame.setVisible(true);
            	court.reset();
            	username = usernameTF.getText(); 
            }
        });
        startPanel.add(startGame);
    	startGame.setEnabled(false);

        
    	startPanel.add(usernameTF);
    	DocumentListener usernameChecker = new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				if (checkWord(usernameTF.getText())) {
					startGame.setEnabled(true);
				}
				else {
					startGame.setEnabled(false);
				}
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if (checkWord(usernameTF.getText())) {
					startGame.setEnabled(true);
				}
				else {
					startGame.setEnabled(false);
				}
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if (usernameTF.getText().length() == 0) {
					startGame.setEnabled(false);
				}
				else {
					startGame.setEnabled(false);
				}
			}
    	};
    	usernameTF.getDocument().addDocumentListener(usernameChecker); 
        
        final JButton leaderboard = new JButton("Leaderboard");
    	leaderboard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	frame.setVisible(false);
            	startFrame.setVisible(false);
            	instructionsFrame.setVisible(false);
            	leaderboardFrame.setVisible(true);
            }
    	});
    	startPanel.add(leaderboard);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel instructionlabel = new JLabel("Press Enter on your keyboard to see check whether your input is accepted by the system");
        startPanel.add(instructionlabel);
        
        JLabel gameInstructions = new JLabel("<html><body>"
        		+ "      The main goal of this game is to make it to the end and touch the yellow ball<br>"
        		+ "There are obstacles, and blocks that will give you a better score standing in your way<br>"
        		+ "There are small black squares falling from the ceiling. Those are rocks, and if you touch them,"
        		+ "you die. <br>"
        		+ "There is also a reddish orange rectanglular thing falling from the ceiling. If you <br>"
        		+ "touch that, you die too. There is a rectangle in the middle of the stage that has two states:<br>"
        		+ "cyan and red. If you touch it when it is red, you die, and if you try to pass it when it is cyan,<br>"
        		+ "you survive. There is a magenta square block as well. It is a teleportation block. If you touch that <br>"
        		+ "you get sent back to the start."
        		+ "If you jump and touch the blue blocks past the spikes, you can lower your score, giving you a <br>"
        		+ "a better score, as lower scores are better in this game. <br>"
        		+ "The controls are done by your arrow keys, with each of the arrow keys sending you in a different <br>"
        		+ "direction. Left arrow sends you left, right arrow sends you write, etc. But the down arrow does nothing.<br>"
        		+ "a special control is also in place. You can teleport yourself back to spawn to avoid dying<br>"
        		+ "if you press the letter t key. Use it wisely, because more time spent means you have a lower score!<br>"
        		+ "Also, I improved the arrow key controls a little so that the square stops only in an x or y velocity<br>"
        		+ "if an arrow key relating to an x or y coordinate was released."
        		+ "The only usernames suitable in this game are ones comprised of numbers and letters, and ones that are 14 or less characters."
        		+ "</body></html>"
        		);
        instructionsPanel.add(gameInstructions); 
        
        final JButton instructionsButton = new JButton("Instructions");
    	instructionsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	frame.setVisible(false);
            	startFrame.setVisible(false);
            	leaderboardFrame.setVisible(false);
            	instructionsFrame.setVisible(true);
            }
    	});
    	startPanel.add(instructionsButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    	
    	final JButton toStart = new JButton("To Start");
    	toStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	frame.setVisible(false);
            	instructionsFrame.setVisible(false);
            	leaderboardFrame.setVisible(false);
            	startFrame.setVisible(true);

            }
    	});
    	leaderboardPanel.add(toStart);
    	instructionsPanel.add(toStart);
    	leaderboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	
    	
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}