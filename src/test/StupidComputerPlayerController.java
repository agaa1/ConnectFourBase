package test;

import java.util.Set;

import server.advanced.Logging;
import server.base.Action;
import server.base.Board;
import server.base.PlayerColor;
import server.control.PlayerController;
import server.exceptions.GameException;

public class StupidComputerPlayerController extends PlayerController {

	private String name = "";
	private int delay = 1000;

	public StupidComputerPlayerController(String name, int delay) {
		super();
		this.name = name;
		this.delay = delay;
	}

	private void sleep(long sleep) {
		try {
			Thread.sleep(sleep * delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void receiveJoinConfirmation(PlayerColor color) {
		sleep(2);
		System.out.println("Computer (" + name + ") joined as " + color);

	}

	@Override
	public void receiveUpdate(Board newBoard, Action latestMove) {
		sleep(1);
		if (Logging.LOGS)
			System.out.println("Computer (" + name
					+ ") received updated board for move: " + latestMove);

	}

	@Override
	public void receiveTurnPrompt(Set<Action> availableActions) {
		sleep(1);
		System.out.println("Computer (" + name + ") received turn prompt");
		try {
			sleep(3);
			Action action = availableActions.iterator().next();
			System.out.println("Computer (" + name + ") sending move: "
					+ action);
			sendSubmitMove(action);
		} catch (GameException e) {
			System.out.println("Stupid computer broke it!");
		}
	}

	@Override
	public void receiveWin(PlayerColor color) {
		if (getColor().equals(color)) {
			System.out.println("Computer (" + name + ") has won!");
		} else {
			System.out.println("Computer (" + name + ") has lost!");
		}
		sleep(1);
	}

	@Override
	public void receiveDraw() {
		System.out.println("Computer (" + name + ") has drawn!");
		sleep(1);
	}

}
