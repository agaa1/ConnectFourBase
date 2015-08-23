package run;

import server.base.Board;
import server.base.PlayerColor;
import server.control.GameController;
import server.exceptions.GameException;
import test.CommandLinePlayerController;
import test.StupidComputerPlayerController;

public class Main {

	GameController gc;

	public Main() throws GameException {
		Board testBoard = new Board();

		gc = new GameController(testBoard, PlayerColor.RED);

		CommandLinePlayerController red = new CommandLinePlayerController();
		// StupidComputerPlayerController red = new
		// StupidComputerPlayerController(
		// "Dave", 1000);

		gc.joinGame(red);

		StupidComputerPlayerController yellow = new StupidComputerPlayerController(
				"StupidButtFace", 1000);

		gc.joinGame(yellow);
	}

	private void setupBoard(Board testBoard) {

	}

	public static void main(String[] args) {

		try {
			Main main = new Main();
		} catch (GameException e) {
			e.printStackTrace();
		}

	}
}
