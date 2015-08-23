package server.base;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {

	// Column first, then row
	private Map<Square, Piece> board;

	public Board() {
		board = new HashMap<Square, Piece>();
	}

	public void init() {
		board.clear();
	}

	public Piece getPieceAt(Square location) {
		return board.get(location);
	}

	public Board getTempBoard() {
		Board tempBoard = new Board();
		for (Square location : board.keySet()) {
			tempBoard.addPiece(location, board.get(location).copy());
		}
		return tempBoard;
	}

	public void addPiece(Square location, Piece piece) {
		if (board.containsKey(location)) {
			System.out.println("Error! Trying to add piece to full location: "
					+ location);
		}
		board.put(location, piece);
	}

	public void commitTempBoard(Board tempBoard) {
		this.board.clear();
		this.board.putAll(tempBoard.getBoard());
	}

	public Map<Square, Piece> getBoard() {
		return board;
	}

	public Set<Piece> getPieces(PlayerColor player) {
		Set<Piece> playerPieces = new HashSet<Piece>();
		for (Piece piece : board.values()) {
			if (piece.getColor().equals(player)) {
				playerPieces.add(piece);
			}
		}
		return playerPieces;
	}

	public Set<Action> getMoves() {
		Set<Action> moves = new HashSet<Action>();

		for (int i = 1; i <= 7; i++) {
			Square top = new Square(i, 6);
			if (!board.containsKey(top)) {
				moves.add(new Action(i));
			}
		}
		return moves;
	}

	public Square getNextSquare(int column) {
		for (int row = 1; row <= 6; row++) {
			Square next = new Square(column, row);
			if (!board.containsKey(next)) {
				return next;
			}
		}
		return null;
	}

	public boolean hasWon(PlayerColor player) {
		for (Piece p1 : board.values()) {
			if (p1.getColor().equals(player)) {

				Square s1 = p1.getLocation();

				// Look at all 8 different directions
				for (int c = -1; c <= 1; c++) {
					for (int r = -1; r <= 1; r++) {
						if (r == 0 && c == 0) {
							// Ignore when both row and column diffs are 0
							continue;
						}

						Square s2 = s1.shiftColumn(c).shiftRow(r);
						Square s3 = s2.shiftColumn(c).shiftRow(r);
						Square s4 = s3.shiftColumn(c).shiftRow(r);

						if (!s4.isValidSquare()) {
							continue;
						}

						Piece p2 = board.get(s2);
						Piece p3 = board.get(s3);
						Piece p4 = board.get(s4);

						if (p2 == null || p3 == null || p4 == null) {
							continue;
						}
						if (p2.getColor().equals(player)
								&& p3.getColor().equals(player)
								&& p4.getColor().equals(player)) {
							return true;
						}

					}
				}
			}
		}

		return false;
	}

}
