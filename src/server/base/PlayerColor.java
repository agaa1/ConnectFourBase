package server.base;

public enum PlayerColor {
	YELLOW("Y"), RED("R");

	public String displayCharacter;

	private PlayerColor(String dc) {
		this.displayCharacter = dc;
	}

	public String getDisplayCharacter() {
		return displayCharacter;
	}

	public PlayerColor getOpponent() {
		if (this.equals(RED)) {
			return PlayerColor.YELLOW;
		}
		return PlayerColor.RED;
	}
}
