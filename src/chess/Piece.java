package chess;

public class Piece {
	private char type;
	private int x, y;
	private char color;
	private boolean moved;
	
	public Piece(char type, int x, int y, char color) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.color = color;
		this.moved = false;
	}
	
	public Piece(char type, int x, int y, char color, boolean moved) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.color = color;
		this.moved = moved;
	}
	
	public String toString() {
		return this.type + " " + this.x + " " + this.y + " " + this.color + " " + Boolean.toString(this.isMoved());
	}
	
	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public char getColor() {
		return color;
	}

	public void setColor(char color) {
		this.color = color;
	}

	public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}
	
}
