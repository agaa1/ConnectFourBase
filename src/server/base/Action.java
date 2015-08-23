package server.base;

public class Action implements Comparable<Action> {
	// Number from 1 to 7
	private int position;

	public Action(int pos) {
		this.position = pos;
	}

	@Override
	public String toString() {
		return "Position " + position;
	}

	@Override
	public int compareTo(Action other) {
		return Integer.compare(position, other.getPosition());
	}

	public int getPosition() {
		return position;
	}
}
