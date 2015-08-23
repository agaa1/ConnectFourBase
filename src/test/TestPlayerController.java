package test;

import java.util.Set;

import server.advanced.Logging;
import server.base.Action;
import server.base.Board;
import server.base.PlayerColor;
import server.control.PlayerController;

public class TestPlayerController extends PlayerController {

	private PlayerColor color;

	@Override
	public void receiveJoinConfirmation(PlayerColor color) {
		System.out.println(color + " player has joined!");
		this.color = color;
	}

	@Override
	public void receiveUpdate(Board newBoard, Action latestMove) {
		if (Logging.LOGS)
			System.out.println(color + " player received an update!");
	}

	@Override
	public void receiveTurnPrompt(Set<Action> availableActions) {
		System.out.println(color + " player received an turn prompt!");
		for (Action action : availableActions) {
			System.out.println(action);
		}

	}

	@Override
	public void receiveWin(PlayerColor color) {
		System.out.println(color + " player received an update!");

	}

	@Override
	public void receiveDraw() {
		System.out.println(color + " player received an update!");

	}
}
