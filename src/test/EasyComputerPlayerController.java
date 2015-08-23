package test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import server.advanced.Logging;
import server.base.Action;
import server.base.Board;
import server.base.Piece;
import server.base.PlayerColor;
import server.base.Square;
import server.control.PlayerController;
import server.exceptions.GameException;

public class EasyComputerPlayerController extends PlayerController {

	private String name = "";
	private int delay = 1000;

	private Board latestBoard = null;

	public EasyComputerPlayerController(String name, int delay) {
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
		latestBoard = newBoard;
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
			Map<Action, Integer> losingMoves = new HashMap<Action, Integer>();
			Action bestAction = null;
			for (Action action : availableActions) {
				losingMoves.put(
						action,
						getLosingCountForMove(action, latestBoard, getColor(),
								5));
			}
			int bestLosingMoves = Integer.MAX_VALUE;
			for (Action action : losingMoves.keySet()) {
				if (losingMoves.get(action) < bestLosingMoves) {
					bestLosingMoves = losingMoves.get(action);
					bestAction = action;
				}
			}
			System.out.println("Computer (" + name + ") sending move: "
					+ bestAction);
			sendSubmitMove(bestAction);
		} catch (GameException e) {
			System.out.println("Stupid computer broke it!");
		}
	}

	private int getLosingCountForMove(Action move, Board board,
			PlayerColor player, int movesInFuture) {
		Board tempBoard = board.getTempBoard();
		Square nextMove = tempBoard.getNextSquare(move.getPosition());
		tempBoard.addPiece(nextMove, new Piece(player, nextMove));

		if (movesInFuture == 0 || movesInFuture == 1
				&& player.equals(getOpponent())) {
			return tempBoard.hasWon(player) ? 1 : 0;
		}

		Set<Action> newActions = tempBoard.getMoves();
		PlayerColor newPlayer = player.getOpponent();

		int losses = 0;

		for (Action newAction : newActions) {
			losses += getLosingCountForMove(newAction, tempBoard, newPlayer,
					movesInFuture - 1);
		}

		return losses;
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
