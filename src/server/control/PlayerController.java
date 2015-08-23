package server.control;

import java.util.Set;

import server.base.Action;
import server.base.Board;
import server.base.PlayerColor;
import server.exceptions.GameException;

public abstract class PlayerController {

	private GameController game;
	private PlayerColor color;

	public PlayerController() {
	}

	protected void setControllerHooks(GameController game, PlayerColor color) {
		this.game = game;
		this.color = color;
	}

	public void sendSubmitMove(Action move) throws GameException {
		if (game == null) {
			throw new GameException("Have not joined a game yet!");
		}
		game.sendMove(color, move);
	}

	public void sendWin() throws GameException {
		if (game == null) {
			throw new GameException("Have not joined a game yet!");
		}
		game.win(color);
	}

	public abstract void receiveJoinConfirmation(PlayerColor color);

	public abstract void receiveUpdate(Board newBoard, Action latestMove);

	public abstract void receiveTurnPrompt(Set<Action> availableActions);

	public abstract void receiveWin(PlayerColor color);

	public abstract void receiveDraw();

	protected PlayerColor getColor() {
		return color;
	}

	protected PlayerColor getOpponent() {
		if (color.equals(PlayerColor.RED)) {
			return PlayerColor.YELLOW;
		} else {
			return PlayerColor.RED;
		}
	}
}
