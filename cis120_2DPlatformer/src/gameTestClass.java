import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class gameTestClass {
	private GameCourt testCourt; 
	@Before 
	public void setUp() {
		//initialize gamecourt
        final JLabel status = new JLabel("Running...");
		this.testCourt = new GameCourt(status);
		testCourt.reset();
	}
	
	@Test
	public void testDeathByRock() {
		testCourt.rockGenerator(0, 0, 240);
		for (int i = 0; i < 5000; i++) {
			testCourt.tick();
		}
		assertFalse(testCourt.playing);
	}
	
	@Test
	public void testDeathByLava() {
		testCourt.lavaGenerator(0, 400);
		for (int i = 0; i < 100000; i++) {
			testCourt.tick();
		}
		assertFalse(testCourt.playing); 
	}
	
	@Test 
	public void testTeleport() {
		testCourt.playerSpawner(200, 295);
		for (int i = 0; i < 10000; i++) {
			testCourt.tick();
		}
		assertTrue(testCourt.getPlayerPx() == 0); 
		assertTrue(testCourt.getPlayerPy() == 300);
		//create teleport block method kinda like rock 
		//if intersect then bakc at start????
	}
	
	@Test 
	public void testBlockDoesNotFallThroughGround() {
		for (int i = 0; i < 10000; i++) {
			testCourt.tick();
		}
		assertTrue(testCourt.getPlayerPy() == 300);
	}
	
	@Test
	public void testSpike() {
		testCourt.playerSpawner(400, 100);
		for (int i = 0; i < 10000; i++) {
			testCourt.tick();
		}
		assertFalse(testCourt.playing); 
	}
	
}
