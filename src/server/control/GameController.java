package server.control;

import java.util.HashSet;
import java.util.Set;

import server.base.Action;
import server.base.Board;
import server.base.Piece;
import server.base.PlayerColor;
import server.base.Square;
import server.exceptions.GameException;

public class GameController {

	private Board board;
	private PlayerColor startingPlayer;

	private PlayerColor turn;
	private Set<Action> availableActions;

	private PlayerController yellow;
	private PlayerController red;

	private PlayerColor winner;

	public GameController() {
		this(PlayerColor.YELLOW);
	}

	public GameController(Board board) {
		this(board, PlayerColor.YELLOW);
	}

	public GameController(PlayerColor startingPlayer) {
		this.board = new Board();
		this.board.init();
		this.availableActions = new HashSet<Action>();
		this.startingPlayer = startingPlayer;
		this.winner = null;
	}

	public GameController(Board board, PlayerColor startingPlayer) {
		this.board = board;
		this.availableActions = new HashSet<Action>();
		this.startingPlayer = startingPlayer;
		this.winner = null;
	}

	private PlayerController getCurrentPlayer() {
		return getPlayer(turn);
	}

	private PlayerController getPlayer(PlayerColor color) {
		if (color == PlayerColor.YELLOW) {
			return red;
		} else {
			return yellow;
		}
	}

	public void joinGame(PlayerController controller) throws GameException {
		if (yellow == null) {
			yellow = controller;
			controller.setControllerHooks(this, PlayerColor.RED);
			yellow.receiveJoinConfirmation(PlayerColor.RED);
		} else if (red == null) {
			red = controller;
			controller.setControllerHooks(this, PlayerColor.YELLOW);
			red.receiveJoinConfirmation(PlayerColor.YELLOW);
			startGame();
		} else {
			// Error only 2 players!!!
			throw new GameException("Only 2 players can play Connect Four!");
		}

	}

	private void startGame() {
		turn = startingPlayer;
		calculateAvailableActions(turn);

		getCurrentPlayer().receiveTurnPrompt(availableActions);
	}

	protected void sendMove(PlayerColor color, Action move)
			throws GameException {
		// Reject Invalid Moves
		if (color != turn) {
			throw new GameException(
					"Tried to make a move but it is not your turn!");
		}
		if (!availableActions.contains(move)) {
			throw new GameException("Tried to make an invalid move!");
		}

		// Process Move
		Board tempBoard = board.getTempBoard();
		Square nextMove = tempBoard.getNextSquare(move.getPosition());
		tempBoard.addPiece(nextMove, new Piece(turn, nextMove));

		board.commitTempBoard(tempBoard);

		yellow.receiveUpdate(board, move);
		red.receiveUpdate(board, move);

		// Automatic loss if no pieces left
		if (board.hasWon(turn)) {
			win(turn);
			return;
		}

		// Next Turn
		if (color == PlayerColor.YELLOW) {
			turn = PlayerColor.RED;
		} else {
			turn = PlayerColor.YELLOW;
		}

		calculateAvailableActions(turn);

		// Automatic loss if no moves available
		if (availableActions.isEmpty()) {
			draw();
			return;
		}

		// Send prompt for next move
		getCurrentPlayer().receiveTurnPrompt(availableActions);

	}

	private void calculateAvailableActions(PlayerColor player) {
		availableActions.clear();

		availableActions.addAll(calculateAvailableMoves(player));
	}

	private Set<Action> calculateAvailableMoves(PlayerColor player) {
		return board.getMoves();
	}

	public void win(PlayerColor color) {
		if (color == PlayerColor.YELLOW) {
			winner = PlayerColor.YELLOW;
		} else {
			winner = PlayerColor.RED;
		}
		yellow.receiveWin(winner);
		red.receiveWin(winner);
	}

	public void draw() {
		yellow.receiveDraw();
		red.receiveDraw();
	}
}
