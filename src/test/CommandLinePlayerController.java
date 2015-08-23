package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

public class CommandLinePlayerController extends PlayerController {

	@Override
	public void receiveJoinConfirmation(PlayerColor color) {
		System.out.println(getColor() + ":You have joined!");

	}

	@Override
	public void receiveUpdate(Board newBoard, Action latestMove) {
		if (Logging.LOGS)
			System.out.println(getColor() + ":You received update for move: "
					+ latestMove + "!");

		printBoard(newBoard);

	}

	private void printBoard(Board newBoard) {
		System.out.println("-------");
		for (int row = 6; row >= 1; row--) {
			for (int column = 1; column <= 7; column++) {
				Square sq = new Square(column, row);
				Piece pc = newBoard.getPieceAt(sq);
				if (pc == null) {
					System.out.print(" ");
				} else {
					System.out.print(pc.getColor().getDisplayCharacter());
				}
			}
			System.out.println();
		}
		System.out.println("-------");

	}

	@Override
	public void receiveTurnPrompt(Set<Action> availableActions) {
		System.out.println(getColor() + ":Your turn!");

		List<Action> sortable = new ArrayList<Action>();
		sortable.addAll(availableActions);
		Collections.sort(sortable);

		Map<Integer, Action> theMap = new HashMap<Integer, Action>();
		int i = 1;
		for (Action action : sortable) {
			theMap.put(i, action);
			i++;
		}

		for (i = 1; i <= theMap.size(); i++) {
			System.out.println(i + " - " + theMap.get(i));
		}

		int input = -1;

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			System.out.print(getColor() + ":Enter number for move: ");

			try {
				input = Integer.parseInt(br.readLine());
			} catch (Exception e) {
				System.out.println("Invalid Input!");
			}
			if (theMap.keySet().contains(input)) {
				break;
			}
		}
		try {
			sendSubmitMove(theMap.get(input));
		} catch (GameException e) {
			System.exit(0);
		}
	}

	@Override
	public void receiveWin(PlayerColor color) {
		if (getColor() == color) {
			System.out.println(getColor() + ":You win!");
		} else {
			System.out.println(getColor() + ":You lose!");
		}

	}

	@Override
	public void receiveDraw() {
		System.out.println(getColor() + ":You draw!");
	}

}
