package server.exceptions;

public class GameException extends Throwable {

	private static final long serialVersionUID = 1L;

	private String details;

	public GameException(String details) {
		this.details = details;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}
