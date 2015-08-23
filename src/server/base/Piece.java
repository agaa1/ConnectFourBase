package server.base;

public class Piece {

	private PlayerColor color;

	private Square location;

	public Piece(PlayerColor color, Square location) {
		this.color = color;
		this.location = location;
	}

	public PlayerColor getColor() {
		return color;
	}

	public void setColor(PlayerColor color) {
		this.color = color;
	}

	public Square getLocation() {
		return location;
	}

	public void setLocation(Square location) {
		this.location = location;
	}

	public Piece copy() {
		Piece copyOfPiece = new Piece(color, location.copy());
		return copyOfPiece;
	}

	@Override
	public String toString() {
		return "Piece [color=" + color + ", location=" + location + "]";
	}

}
