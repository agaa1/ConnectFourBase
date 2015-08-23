package server.base;

public class Square implements Comparable<Square> {
	private int column;
	private int row;

	public Square(int column, int row) {
		this.column = column;
		this.row = row;
	}

	public Square copy() {
		return new Square(column, row);
	}

	public Square shiftRow(int shift) {
		return new Square(column, row + shift);
	}

	public Square shiftColumn(int shift) {
		return new Square(column + shift, row);
	}

	public boolean isValidSquare() {
		if (column >= 1 && column <= 7 && row >= 1 && row <= 6) {
			return true;
		}
		return false;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Square other = (Square) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Square [column=" + column + ", row=" + row + "]";
	}

	@Override
	public int compareTo(Square other) {
		int rowValue = (row < other.getRow() ? -1 : (row == other.getRow() ? 0
				: 1));
		if (rowValue == 0) {
			return (column < other.getColumn() ? -1 : (column == other
					.getColumn() ? 0 : 1));
		} else {
			return rowValue;
		}
	}
}
