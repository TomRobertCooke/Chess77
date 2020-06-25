package chess;

import java.util.ArrayList;
import java.util.Scanner;

public class Chess {
	
	public static int[] whiteKing = new int[2];
	public static int[] blackKing = new int[2];
	public static boolean check = false;
	
	public static void main(String[] args) {
		
		//Initialize board
		
		//System.out.println("Initializing board");
		Piece[][] board = new Piece[8][8];
		
		int[] lastMove = new int[4];
		
		char color = ' ';
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 8; i++) {
				if (j == 1) {
					board[i][j] = new Piece('p', i, j, 'b');
				} else if (j == 6) {
					board[i][j] = new Piece('p', i, j, 'w');
				} else if (j >= 2 && j <= 5) {
					if (j % 2 == 0) {
						if (i % 2 == 0) {
							board[i][j] = new Piece(' ', i, j, ' ');
						} else {
							board[i][j] = new Piece('#', i, j, '#');
						}
					} else {
						if (i % 2 == 0) {
							board[i][j] = new Piece('#', i, j, '#');
						} else {
							board[i][j] = new Piece(' ', i, j, ' ');
						}
					}
				} else {
					if (j == 7) {
						color = 'w';
					} else {
						color = 'b';
					}
					if (i == 0 || i == 7) {
						board[i][j] = new Piece('R', i, j, color);
					} else if (i == 1 || i == 6) {
						board[i][j] = new Piece('N', i, j, color);
					} else if (i == 2 || i == 5) {
						board[i][j] = new Piece('B', i, j, color);
					} else if (i == 3) {
						board[i][j] = new Piece('Q', i, j, color);
					} else if (i == 4) {
						board[i][j] = new Piece('K', i, j, color);
					}
				}
			}
		}
		
		whiteKing[0] = 4;
		whiteKing[1] = 7;
		blackKing[0] = 4;
		blackKing[1] = 0;
		
		printBoard(board);
		
		boolean drawProposed = false;
		
		Scanner in = new Scanner(System.in);
		char move = 'w';
		boolean finished = false;
		char winner = ' ';
		
		while(!finished) {
			
			if (move == 'w') {
				System.out.print("White's move: ");
			} else {
				System.out.print("Black's move: ");
			}
			
			String userMove = in.nextLine();
			System.out.println();
			
			if (userMove.length() < 4) {
				System.out.println("Illegal move, try again\n");
				continue;
			}
			
			if (userMove.equalsIgnoreCase("resign")) {
				finished = true;
				if (move == 'w') {
					winner = 'b';
				} else {
					winner = 'w';
				}
				continue;
			}
			
			
			if (userMove.equalsIgnoreCase("draw") && drawProposed) {
				finished = true;
				winner = 'd';
				continue;
			}
			
			if (drawProposed) {
				drawProposed = false;
			}
			
			char promotedPiece = 'Q';
			if (userMove.length() == 7) {
				if (userMove.charAt(6) == 'p' || userMove.charAt(6) == 'R' || userMove.charAt(6) == 'N' || userMove.charAt(6) == 'B' || userMove.charAt(6) == 'Q') {
					promotedPiece = userMove.charAt(6);
				} else {
					System.out.println("Illegal move, try again\n");
					continue;
				}
			}
			
			if (userMove.length() == 11) {
				if (userMove.substring(6, 11).equalsIgnoreCase("draw?")) {
					drawProposed = true;
				}
			}
			
			
			
			char fromFile = userMove.charAt(0);
			char toFile = userMove.charAt(3);
			if (fromFile < 'a' || fromFile > 'h' || toFile < 'a' || toFile > 'h') {
				System.out.println("Illegal move, try again\n");
				continue;
			}
			
			int fromRank = 0, toRank = 0;
			if (!Character.isDigit(userMove.charAt(1)) || !Character.isDigit(userMove.charAt(4))) {
				System.out.println("Illegal move, try again\n");
				continue;
			} else {
				fromRank = Integer.parseInt(userMove.substring(1, 2));
				toRank = Integer.parseInt(userMove.substring(4, 5));
				if (fromRank > 8 || fromRank < 1 || toRank > 8 || toRank < 1) {
					System.out.println("Illegal move, try again\n");
					continue;
				}
			}
			
			fromRank = 8 - fromRank; //Transformed into their array counterparts
			toRank = 8 - toRank;
			
			int fromFileNum = 0, toFileNum = 0;
			
			switch (fromFile) {
				case 'b':
					fromFileNum = 1;
					break;
				case 'c':
					fromFileNum = 2;
					break;
				case 'd':
					fromFileNum = 3;
					break;
				case 'e':
					fromFileNum = 4;
					break;
				case 'f':
					fromFileNum = 5;
					break;
				case 'g':
					fromFileNum = 6;
					break;
				case 'h':
					fromFileNum = 7;
					break;
				default:
					fromFileNum = 0;
			}
			
			switch (toFile) {
				case 'b':
					toFileNum = 1;
					break;
				case 'c':
					toFileNum = 2;
					break;
				case 'd':
					toFileNum = 3;
					break;
				case 'e':
					toFileNum = 4;
					break;
				case 'f':
					toFileNum = 5;
					break;
				case 'g':
					toFileNum = 6;
					break;
				case 'h':
					toFileNum = 7;
					break; 
				default:
					toFileNum = 0;
			}
			
			if (board[fromFileNum][fromRank].getColor() != move) {
				System.out.println("Illegal move, try again\n");
				continue;
			}
			
			if (!move(board, fromFileNum, fromRank, toFileNum, toRank, lastMove, promotedPiece)) {
				System.out.println("Illegal move, try again\n");
				continue;
			}
			
			printBoard(board);
			
			//save last move for en passant
			lastMove[0] = fromFileNum;
			lastMove[1] = fromRank;
			lastMove[2] = toFileNum;
			lastMove[3] = toRank;
			
			char opponent = ' ';
			if (move == 'w') {
				opponent = 'b';
			} else {
				opponent = 'w';
			}
			
			if (checkmate(board, move, lastMove)) {
				System.out.println("Checkmate\n");
				winner = move;
				finished = true;
				continue;
			} else if (!hasMove(board, opponent, lastMove)) {
				winner = 'd';
				finished = true;
				continue;
			}else if (check) {
				System.out.println("Check\n");
			}
			
			//System.out.println("Move: " + move + ", " + fromFileNum + "" + fromRank + " " + toFileNum + "" + toRank);
			
			/*
			things to do:
			1. basic movements of pieces//
			2. castling -> king move//
			3. enpassant//
			4. promotion//
			5. identification of a check//
			6. identification of checkmate//
			7. identification of an illegal move//
			8. resign//
			9. draw//
			10. drawing board display as specified//
			*/
			if (move == 'w') { 
				move = 'b';
			} else {
				move = 'w';
			}
			
		}
		
		if (winner == 'w') {
			System.out.println("White wins");
		} else if (winner == 'b') {
			System.out.println("Black wins");
		} else if (winner == 'd') {
			System.out.println("draw");
		}
		
		in.close();
		
	}
	
	
	
	public static boolean hasMove(Piece[][] board, char move, int[] lastMove) {
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j].getColor() == move) {
					pieces.add(board[i][j]);
					if (pieces.size() == 16) break;
				}
			}
		}
		
		while (pieces.size() > 0) {
			Piece current = pieces.get(0);
			
			if (testMoves(board, current, lastMove)) {
				return true;
			}
			
			pieces.remove(0);
		}
		
		return false;
	}
	
	
	
	public static boolean testMoves(Piece[][] board, Piece testMe, int[] lastMove) {
		char type = testMe.getType();
		char player = testMe.getColor();
		int x = testMe.getX();
		int y = testMe.getY();
		Piece[][] tester = new Piece[8][8];
		ArrayList<Piece> dest = new ArrayList<Piece>();
		
		
		
		if (type == 'K' ) {
			
			if (y < 7 && board[x][y+1].getColor() != player) dest.add(board[x][y+1]);
			if (y > 0 && board[x][y-1].getColor() != player) dest.add(board[x][y-1]);
			if (x < 7 && board[x+1][y].getColor() != player) dest.add(board[x+1][y]);
			if (x > 0 && board[x-1][y].getColor() != player) dest.add(board[x-1][y]);
			if (x < 7 && y < 7 && board[x+1][y+1].getColor() != player) dest.add(board[x+1][y+1]);
			if (x > 0 && y < 7 && board[x-1][y+1].getColor() != player) dest.add(board[x-1][y+1]);
			if (x > 0 && y > 0 && board[x-1][y-1].getColor() != player) dest.add(board[x-1][y-1]);
			if (x < 7 && y > 0 && board[x+1][y-1].getColor() != player) dest.add(board[x+1][y-1]);
			
		} else if (type == 'Q') {
			
			for (int i = x; i < 8; i++) {//Horizontal
				if (board[i][y].getColor() != player) {
					dest.add(board[i][y]);
				} 
				if (i != x && board[i][y].getColor() != ' ' && board[i][y].getColor() != '#') {
					break;
				}
			}
			for (int i = x; i >= 0; i--) {
				if (board[i][y].getColor() != player) {
					dest.add(board[i][y]);
				} 
				if (i != x && board[i][y].getColor() != ' ' && board[i][y].getColor() != '#') {
					break;
				}
			}
			for (int j = y; j < 8; j++) {//Vertical
				if (board[x][j].getColor() != player) {
					dest.add(board[x][j]);
				} 
				if (j != y && board[x][j].getColor() != ' ' && board[x][j].getColor() != '#') {
					break;
				}
			}
			for (int j = y; j >= 0; j--) {
				if (board[x][j].getColor() != player) {
					dest.add(board[x][j]);
				} 
				if (j != y && board[x][j].getColor() != ' ' && board[x][j].getColor() != '#') {
					break;
				}
			}
			int i = x, j = y;
			while (i < 8 && j < 8) { //down right
				if (board[i][j].getColor() != player) {
					dest.add(board[i][j]);
				} 
				if (j != y && i != x && board[i][j].getColor() != ' ' && board[i][j].getColor() != '#') {
					break;
				}
				i++;
				j++;
			}
			i = x;
			j = y;
			while (i >= 0 && j < 8) { //down left
				if (board[i][j].getColor() != player) {
					dest.add(board[i][j]);
				} 
				if (j != y && i != x && board[i][j].getColor() != ' ' && board[i][j].getColor() != '#') {
					break;
				}
				i--;
				j++;
			}
			i = x;
			j = y;
			while (i >= 0 && j >= 0) { //up left
				if (board[i][j].getColor() != player) {
					dest.add(board[i][j]);
				} 
				if (j != y && i != x && board[i][j].getColor() != ' ' && board[i][j].getColor() != '#') {
					break;
				}
				i--;
				j--;
			}
			i = x;
			j = y;
			while (i < 8 && j >= 0) { //up right
				if (board[i][j].getColor() != player) {
					dest.add(board[i][j]);
				} 
				if (j != y && i != x && board[i][j].getColor() != ' ' && board[i][j].getColor() != '#') {
					break;
				}
				i++;
				j--;
			}
			
		} else if (type == 'B') {
			
			int i = x, j = y;
			while (i < 8 && j < 8) { //down right
				if (board[i][j].getColor() != player) {
					dest.add(board[i][j]);
				} 
				if (j != y && i != x && board[i][j].getColor() != ' ' && board[i][j].getColor() != '#') {
					break;
				}
				i++;
				j++;
			}
			i = x;
			j = y;
			while (i >= 0 && j < 8) { //down left
				if (board[i][j].getColor() != player) {
					dest.add(board[i][j]);
				} 
				if (j != y && i != x && board[i][j].getColor() != ' ' && board[i][j].getColor() != '#') {
					break;
				}
				i--;
				j++;
			}
			i = x;
			j = y;
			while (i >= 0 && j >= 0) { //up left
				if (board[i][j].getColor() != player) {
					dest.add(board[i][j]);
				} 
				if (j != y && i != x && board[i][j].getColor() != ' ' && board[i][j].getColor() != '#') {
					break;
				}
				i--;
				j--;
			}
			i = x;
			j = y;
			while (i < 8 && j >= 0) { //up right
				if (board[i][j].getColor() != player) {
					dest.add(board[i][j]);
				} 
				if (j != y && i != x && board[i][j].getColor() != ' ' && board[i][j].getColor() != '#') {
					break;
				}
				i++;
				j--;
			}
			
		} else if (type == 'N') {
			
			if (x + 2 <= 7 && y + 1 <= 7 && board[x+2][y+1].getColor() != player) return true; 
			if (x + 1 <= 7 && y + 2 <= 7 && board[x+1][y+2].getColor() != player) return true;
			if (x - 2 >= 0 && y + 1 <= 7 && board[x-2][y+1].getColor() != player) return true;
			if (x - 1 >= 0 && y + 2 <= 7 && board[x-1][y+2].getColor() != player) return true;
			if (x - 2 >= 0 && y - 1 >= 0 && board[x-2][y-1].getColor() != player) return true;
			if (x - 1 >= 0 && y - 2 >= 0 && board[x-1][y-2].getColor() != player) return true;
			if (x + 2 <= 7 && y - 1 >= 0 && board[x+2][y-1].getColor() != player) return true; 
			if (x + 1 <= 7 && y - 2 >= 0 && board[x+1][y-2].getColor() != player) return true;
			
		} else if (type == 'R') {
			
			for (int i = x; i < 8; i++) {
				if (board[i][y].getColor() != player) {
					dest.add(board[i][y]);
				} 
				if (i != x && board[i][y].getColor() != ' ' && board[i][y].getColor() != '#') {
					break;
				}
			}
			for (int i = x; i >= 0; i--) {
				if (board[i][y].getColor() != player) {
					dest.add(board[i][y]);
				} 
				if (i != x && board[i][y].getColor() != ' ' && board[i][y].getColor() != '#') {
					break;
				}
			}
			for (int j = y; j < 8; j++) {
				if (board[x][j].getColor() != player) {
					dest.add(board[x][j]);
				} 
				if (j != y && board[x][j].getColor() != ' ' && board[x][j].getColor() != '#') {
					break;
				}
			}
			for (int j = y; j >= 0; j--) {
				if (board[x][j].getColor() != player) {
					dest.add(board[x][j]);
				} 
				if (j != y && board[x][j].getColor() != ' ' && board[x][j].getColor() != '#') {
					break;
				}
			}
			
		} else if (type == 'p') {
			
			if (player == 'w') {
				if (y - 1 >= 0 && board[x][y-1].getColor() != 'w' && board[x][y-1].getColor() != 'b') dest.add(board[x][y-1]);
				if (y - 2 >= 0 && board[x][y-2].getColor() != 'w' && board[x][y-2].getColor() != 'b') dest.add(board[x][y-2]);
				if (x - 1 >= 0 && y - 1 >= 0 && board[x-1][y-1].getColor() != 'w') dest.add(board[x-1][y-1]);
				if (x + 1 <= 7 && y - 1 >= 0 && board[x+1][y-1].getColor() != 'w') dest.add(board[x+1][y-1]);
			} else {
				if (y + 1 >= 0 && board[x][y+1].getColor() != 'w' && board[x][y+1].getColor() != 'b') dest.add(board[x][y+1]);
				if (y + 2 >= 0 && board[x][y+2].getColor() != 'w' && board[x][y+2].getColor() != 'b') dest.add(board[x][y+2]);
				if (x + 1 <= 7 && y + 1 >= 0 && board[x+1][y+1].getColor() != 'b') dest.add(board[x+1][y+1]);
				if (x - 1 >= 0 && y + 1 >= 0 && board[x-1][y+1].getColor() != 'b') dest.add(board[x-1][y+1]);
			}
			
		}
		
		
		
		while (dest.size() > 0) {
			
			Piece current = dest.get(0);
			boardCopy(board, tester);
			switch (type) {
			case 'R':
				if (rookMove(tester, x, y, current.getX(), current.getY()) && !checked(tester, player)) {
					return true;
				}
				break;
			case 'N':
				if (knightMove(tester, x, y, current.getX(), current.getY()) && !checked(tester, player)) {
					return true;
				}
				break;
			case 'B':
				if (bishopMove(tester, x, y, current.getX(), current.getY()) && !checked(tester, player)) {
					return true;
				}
				break;
			case 'Q':
				if (queenMove(tester, x, y, current.getX(), current.getY()) && !checked(tester, player)) {
					return true;
				}
				break;
			case 'K':
				if (kingMove(tester, x, y, current.getX(), current.getY()) && !checked(tester, player)) {
					return true;
				}
				break;
			default:
				if (pawnMove(tester, x, y, current.getX(), current.getY(), lastMove, 'Q') && !checked(tester, player)) {
					return true;
				}
			}
			dest.remove(0);
			
		}
		
		
		return false;
	}
	
	

	public static void printBoard(Piece[][] board) {
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 8; i++) {
				System.out.print(board[i][j].getColor() + "" + board[i][j].getType() + " ");
			}
			System.out.println((8 - j));
		}
		System.out.println(" a  b  c  d  e  f  g  h\n");
	}
	
	
	
	public static boolean checked(Piece[][] board, char move) {
		if (move == 'w') {
			return attacked(board, move, whiteKing[0], whiteKing[1]);
		} else {
			return attacked(board, move, blackKing[0], blackKing[1]);
		}
	}
	
	
	
	public static boolean attacked(Piece[][] board, char move, int x, int y) {
		char opponent = ' ';
		if (move == 'w') {
			opponent = 'b';
		} else {
			opponent = 'w';
		}
		//System.out.println(x + " " + y);
		//Check for knight attacks first, eight possible in total
		//System.out.println("knight attack");
		if (x + 2 <= 7 && y + 1 <= 7 && board[x+2][y+1].getType() == 'N' && board[x+2][y+1].getColor() == opponent) {//bottom right
			return true;
		} else if (x + 1 <= 7 && y + 2 <= 7 && board[x+1][y+2].getType() == 'N' && board[x+1][y+2].getColor() == opponent) {
			return true;
		} else if (x - 2 >= 0 && y + 1 <= 7 && board[x-2][y+1].getType() == 'N' && board[x-2][y+1].getColor() == opponent) {//bottom left
			return true; 
		} else if (x - 1 >= 0 && y + 2 <= 7 && board[x-1][y+2].getType() == 'N' && board[x-1][y+2].getColor() == opponent) {
			return true;
		} else if (x - 2 >= 0 && y - 1 >= 0 && board[x-2][y-1].getType() == 'N' && board[x-2][y-1].getColor() == opponent) {//top left
			return true; 
		} else if (x - 1 >= 0 && y - 2 >= 0 && board[x-1][y-2].getType() == 'N' && board[x-1][y-2].getColor() == opponent) {
			return true;
		} else if (x + 2 <= 7 && y - 1 >= 0 && board[x+2][y-1].getType() == 'N' && board[x+2][y-1].getColor() == opponent) {//top right
			return true; 
		} else if (x + 1 <= 7 && y - 2 >= 0 && board[x+1][y-2].getType() == 'N' && board[x+1][y-2].getColor() == opponent) {
			return true;
		}
		//System.out.println("queen or rook horizontal");
		//Check for horizontal
		for (int i = x; i < 8; i++) {
			if ( (board[i][y].getType() == 'R' || board[i][y].getType() == 'Q') && (board[i][y].getColor() == opponent) ) {
				System.out.println(i + " " + y + " " + opponent + " " + board[i][y].getType());
				return true;
			} else if (i != x && board[i][y].getColor() != ' ' && board[i][y].getColor() != '#') {
				break;
			}
		}
		for (int i = x; i >= 0; i--) {
			if ( (board[i][y].getType() == 'R' || board[i][y].getType() == 'Q') && (board[i][y].getColor() == opponent) ) {
				System.out.println(i + " " + y + " " + opponent + " " + board[i][y].getType());
				return true;
			} else if (i != x && board[i][y].getColor() != ' ' && board[i][y].getColor() != '#') {
				break;
			}
		}
		//System.out.println("queen or rook vertical");
		//Check for vertical
		for (int j = y; j < 8; j++) {
			if ( (board[x][j].getType() == 'R' || board[x][j].getType() == 'Q') && (board[x][j].getColor() == opponent) ) {
				return true;
			} else if (j != y && board[x][j].getColor() != ' ' && board[x][j].getColor() != '#') {
				break;
			}
		}
		for (int j = y; j >= 0; j--) {
			if ( (board[x][j].getType() == 'R' || board[x][j].getType() == 'Q') && (board[x][j].getColor() == opponent) ) {
				return true;
			} else if (j != y && board[x][j].getColor() != ' ' && board[x][j].getColor() != '#') {
				break;
			}
		}
		//System.out.println("bishop or queen");
		//Check for all four diagonals
		int i = x, j = y;
		while (i < 8 && j < 8) { //down right
			if ( (board[i][j].getType() == 'B' || board[i][j].getType() == 'Q') && (board[i][j].getColor() == opponent) ) {
				return true;
			} else if (j != y && i != x && board[i][j].getColor() != ' ' && board[i][j].getColor() != '#') {
				break;
			}
			i++;
			j++;
		}
		i = x;
		j = y;
		while (i >= 0 && j < 8) { //down right
			if ( (board[i][j].getType() == 'B' || board[i][j].getType() == 'Q') && (board[i][j].getColor() == opponent) ) {
				return true;
			} else if (j != y && i != x && board[i][j].getColor() != ' ' && board[i][j].getColor() != '#') {
				break;
			}
			i--;
			j++;
		}
		i = x;
		j = y;
		while (i >= 0 && j >= 0) { //down right
			if ( (board[i][j].getType() == 'B' || board[i][j].getType() == 'Q') && (board[i][j].getColor() == opponent) ) {
				return true;
			} else if (j != y && i != x && board[i][j].getColor() != ' ' && board[i][j].getColor() != '#') {
				break;
			}
			i--;
			j--;
		}
		i = x;
		j = y;
		while (i < 8 && j >= 0) { //down right
			if ( (board[i][j].getType() == 'B' || board[i][j].getType() == 'Q') && (board[i][j].getColor() == opponent) ) {
				return true;
			} else if (j != y && i != x && board[i][j].getColor() != ' ' && board[i][j].getColor() != '#') {
				break;
			}
			i++;
			j--;
		}
		//System.out.println("pawn attack");
		//Check for Pawn attack
		if (move == 'w') {
			if (x < 7 && y > 0) {
				if (board[x+1][y-1].getColor() == 'b' && board[x+1][y-1].getType() == 'p') {
					return true;
				}
			}
			if (x > 0 && y > 0) {
				if (board[x-1][y-1].getColor() == 'b' && board[x-1][y-1].getType() == 'p') {
					return true;
				}
			}
		} else {
			if (x < 7 && y < 7) {
				if (board[x+1][y+1].getColor() == 'w' && board[x+1][y+1].getType() == 'p') {
					return true;
				}
			}
			if (x > 0 && y < 7) {
				if (board[x-1][y+1].getColor() == 'w' && board[x-1][y+1].getType() == 'p') {
					return true;
				}
			}
		}
		//System.out.println("King attack?");
		//Check for King attack
		if (y > 0 && board[x][y-1].getColor() == opponent && board[x][y-1].getType() == 'K') {
			return true;
		} else if (x < 7 && board[x+1][y].getColor() == opponent && board[x+1][y].getType() == 'K') {
			return true;
		} else if (y < 7 && board[x][y+1].getColor() == opponent && board[x][y+1].getType() == 'K') {
			return true;
		} else if (x > 0 && board[x-1][y].getColor() == opponent && board[x-1][y].getType() == 'K') {
			return true;
		} else if (y > 0 && x > 0 && board[x-1][y-1].getColor() == opponent && board[x-1][y-1].getType() == 'K') {
			return true;
		} else if (y > 0 && x < 7 && board[x+1][y-1].getColor() == opponent && board[x+1][y-1].getType() == 'K') {
			return true;
		} else if (y < 7 && x > 0 && board[x-1][y+1].getColor() == opponent && board[x-1][y+1].getType() == 'K') {
			return true;
		} else if (y < 7 && x < 7 && board[x+1][y+1].getColor() == opponent && board[x+1][y+1].getType() == 'K') {
			return true;
		}
		
		return false;
	}
	
	
	
	public static boolean checkmate(Piece[][] board, char move, int[] lastMove) {
		char opponent = 'w';
		if (move == 'w') opponent = 'b';
		if (checked(board, opponent) && !hasMove(board, opponent, lastMove)) {
			return true;
		}
		return false;
	}
	
	
	
	public static boolean move(Piece[][] board, int startFile, int startRank, int endFile, int endRank, int[] lastMove, char promotedPiece) {
		char type = board[startFile][startRank].getType();
		char move = board[startFile][startRank].getColor();
		
		Piece[][] test = new Piece[8][8];
		boardCopy(board, test);
		
		switch (type) {
			case 'R':
				if (!rookMove(test, startFile, startRank, endFile, endRank) || checked(test, move)) {
					return false;
				}
				break;
			case 'N':
				if (!knightMove(test, startFile, startRank, endFile, endRank) || checked(test, move)) {
					return false;
				}
				break;
			case 'B':
				if (!bishopMove(test, startFile, startRank, endFile, endRank) || checked(test, move)) {
					return false;
				}
				break;
			case 'Q':
				if (!queenMove(test, startFile, startRank, endFile, endRank) || checked(test, move)) {
					return false;
				}
				break;
			case 'K':
				if (!kingMove(test, startFile, startRank, endFile, endRank) || checked(test, move)) {
					return false;
				}
				break;
			default:
				if (!pawnMove(test, startFile, startRank, endFile, endRank, lastMove, promotedPiece) || checked(test, move)) {
					return false;
				}
		}
		
		boardCopy(test, board);
		
		return true;
	}
	
	
	
	public static boolean pawnMove(Piece[][] board, int x1, int y1, int x2, int y2, int[] prev, char promotedPiece) {
		int dif = y2 - y1;
		
		if (x1 != x2) { //Pawns eating other pieces, promotion, en passant
			if (board[x1][y1].getColor() == 'w' && dif == -1) {
				if (Math.abs(x1 - x2) == 1) {
					if (board[x2][y2].getColor() == 'b') {
						if (y2 == 0) { //Promotion
							board[x2][y2] = new Piece(promotedPiece, x2, y2, board[x1][y1].getColor(), true);
						} else {
							board[x2][y2] = new Piece(board[x1][y1].getType(), x2, y2, board[x1][y1].getColor(), true);
						}
						makeBlank(board, x1, y1);
						return true;
					} else if (board[x2][y2].getColor() == ' ' || board[x2][y2].getColor() == '#') {
						if (prev[3] == y1 && prev[2] == x2 && board[prev[2]][prev[3]].getColor() == 'b' && board[prev[2]][prev[3]].getType() == 'p' && prev[1] - prev[3] == -2) {
							board[x2][y2] = new Piece(board[x1][y1].getType(), x2, y2, board[x1][y1].getColor(), true);
							makeBlank(board, x1, y1);
							makeBlank(board, x2, y1);
							return true;
						}
					}
				}
			} else if (board[x1][y1].getColor() == 'b' && dif == 1) {
				if (Math.abs(x1 - x2) == 1) {
					if (board[x2][y2].getColor() == 'w') {
						if (y2 == 7) { //Promotion
							board[x2][y2] = new Piece(promotedPiece, x2, y2, board[x1][y1].getColor(), true);
						} else {
							board[x2][y2] = new Piece(board[x1][y1].getType(), x2, y2, board[x1][y1].getColor(), true);
						}
						makeBlank(board, x1, y1);
						return true;
					} else if (board[x2][y2].getColor() == ' ' || board[x2][y2].getColor() == '#') {
						if (prev[3] == y1 && prev[2] == x2 && board[prev[2]][prev[3]].getColor() == 'w' && board[prev[2]][prev[3]].getType() == 'p' && prev[1] - prev[3] == 2) {
							board[x2][y2] = new Piece(board[x1][y1].getType(), x2, y2, board[x1][y1].getColor(), true);
							makeBlank(board, x1, y1);
							makeBlank(board, x2, y1);
							return true;
						}
					}
				}
			}
			return false;
		}
		
		
		if (board[x1][y1].getColor() == 'w') {
			if (dif != -1 && dif != -2) {
				return false;
			} else if (dif == -1) {
				if (board[x2][y2].getColor() == 'w' || board[x2][y2].getColor() == 'b') {
					return false;
				}
			} else {
				if (board[x1][y1 - 1].getColor() == 'w' || board[x1][y1 - 1].getColor() == 'b') {
					return false;
				} else {
					if (board[x1][y1].isMoved() || board[x2][y2].getColor() == 'w' || board[x2][y2].getColor() == 'b') {
						return false;
					}
				}
			}
		} else {
			if (dif != 1 && dif != 2) {
				return false;
			} else if (dif == 1) {
				if (board[x2][y2].getColor() == 'b' || board[x2][y2].getColor() == 'w') {
					return false;
				}
			} else {
				if (board[x1][y1 + 1].getColor() == 'w' || board[x1][y1 + 1].getColor() == 'b') {
					return false;
				} else {
					if (board[x1][y1].isMoved() || board[x2][y2].getColor() == 'b' || board[x2][y2].getColor() == 'w') {
						return false;
					}
				}
			}
		}
	
		//normal pawn movement, promotion
		if ( (board[x1][y1].getColor() == 'w' && y2 == 0) || (board[x1][y1].getColor() == 'b' && y2 == 7) ) { //Promotion
			board[x2][y2] = new Piece(promotedPiece, x2, y2, board[x1][y1].getColor(), true);
		} else {
			board[x2][y2] = new Piece(board[x1][y1].getType(), x2, y2, board[x1][y1].getColor(), true);
		}
		
		makeBlank(board, x1, y1);
		return true;
	}
	
	
	
	public static boolean rookMove(Piece[][] board, int x1, int y1, int x2, int y2) {
		
		if (board[x2][y2].getColor() == board[x1][y1].getColor()) {
			return false;
		}
		
		if (x1 == x2) {
			if (y1 == y2) {
				return false;
			} else {
				int j = y1;
				boolean lower = y1 < y2;
				if (lower) {
					j++;
				} else {
					j--;
				}
				while (j != y2) {
					if (board[x1][j].getColor() != ' ' && board[x1][j].getColor() != '#') {
						return false;
					}
					if (lower) {
						j++;
					} else {
						j--;
					}
				}
			}
		} else if (y1 == y2) {
			int i = x1;
			boolean lower = x1 < x2;
			if (lower) {
				i++;
			} else {
				i--;
			}
			while (i != x2) {
				if (board[i][y1].getColor() != ' ' && board[i][y1].getColor() != '#') {
					return false;
				}
				if (lower) {
					i++;
				} else {
					i--;
				}
			}
		} else {
			return false;
		}
		
		//move the piece here
		board[x2][y2] = new Piece(board[x1][y1].getType(), x2, y2, board[x1][y1].getColor(), true);
		makeBlank(board, x1, y1);
		
		return true;
	}
	
	
	
	public static boolean knightMove(Piece[][] board, int x1, int y1, int x2, int y2) {
		if (board[x2][y2].getColor() == board[x1][y1].getColor()) {
			return false;
		}
		
		if (x1 < x2) {
			if (y1 < y2) {
				if ( !(y2 == y1 + 1 && x2 == x1 + 2) && !(y2 == y1 + 2 && x2 == x1 + 1) ) {
					return false;
				}
			} else if (y1 > y2) {
				if ( !(y2 == y1 - 1 && x2 == x1 + 2) && !(y2 == y1 - 2 && x2 == x1 + 1) ) {
					return false;
				}
			} else {
				return false;
			}
		} else if (x1 > x2) {
			if (y1 < y2) {
				if ( !(y2 == y1 + 1 && x2 == x1 - 2) && !(y2 == y1 + 2 && x2 == x1 - 1) ) {
					return false;
				}
			} else if (y1 > y2) {
				if ( !(y2 == y1 - 1 && x2 == x1 - 2) && !(y2 == y1 - 2 && x2 == x1 - 1) ) {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
		
		//move the piece and make a blank space
		board[x2][y2] = new Piece(board[x1][y1].getType(), x2, y2, board[x1][y1].getColor(), true);
		makeBlank(board, x1, y1);
		
		return true;
	}
	
	
	
	public static boolean bishopMove(Piece[][] board, int x1, int y1, int x2, int y2) {
		if (x1 == x2 || y1 == y2) {
			return false;
		}
		
		if (board[x1][y1].getColor() == board[x2][y2].getColor()) {
			return false;
		}
		
		if (Math.abs(y2 - y1) != Math.abs(x2 - x1)) {
			return false;
		}
		
		boolean xlower = x1 < x2;
		boolean ylower = y1 < y2;
		int i = x1, j = y1;
		
		if (xlower) {
			i++;
		} else {
			i--;
		}
		if (ylower) {
			j++;
		} else {
			j--;
		}
		
		while (i != x2 && j != y2) {
			if (board[i][j].getColor() != ' ' && board[i][j].getColor() != '#') {
				return false;
			}
			
			if (xlower) {
				i++;
			} else {
				i--;
			}
			if (ylower) {
				j++;
			} else {
				j--;
			}
		}
		
		//move the piece and make a blank space
		board[x2][y2] = new Piece(board[x1][y1].getType(), x2, y2, board[x1][y1].getColor(), true);
		makeBlank(board, x1, y1);
		
		return true;
	}
	
	
	
	public static boolean queenMove(Piece[][] board, int x1, int y1, int x2, int y2) {
		if (x1 == x2 && y1 == y2) {
			return false;
		}
		
		if (board[x1][y1].getColor() == board[x2][y2].getColor()) {
			return false;
		}
		
		if (x1 == x2) {
			int j = y1;
			boolean lower = y1 < y2;
			if (lower) {
				j++;
			} else {
				j--;
			}
			while (j != y2) {
				if (board[x1][j].getColor() != ' ' && board[x1][j].getColor() != '#') {
					return false;
				}
				if (lower) {
					j++;
				} else {
					j--;
				}
			}
		} else if (y1 == y2) {
			int i = x1;
			boolean lower = x1 < x2;
			if (lower) {
				i++;
			} else {
				i--;
			}
			while (i != x2) {
				if (board[i][y1].getColor() != ' ' && board[i][y1].getColor() != '#') {
					return false;
				}
				if (lower) {
					i++;
				} else {
					i--;
				}
			}
		} else if (Math.abs(y2 - y1) == Math.abs(x2 -  x1)) {
			boolean xlower = x1 < x2;
			boolean ylower = y1 < y2;
			int i = x1, j = y1;
			
			if (xlower) {
				i++;
			} else {
				i--;
			}
			if (ylower) {
				j++;
			} else {
				j--;
			}
			
			while (i != x2 && j != y2) {
				if (board[i][j].getColor() != ' ' && board[i][j].getColor() != '#') {
					return false;
				}
				
				if (xlower) {
					i++;
				} else {
					i--;
				}
				if (ylower) {
					j++;
				} else {
					j--;
				}
			}
		} else {
			return false;
		}
		
		//move the piece and make a blank space
		board[x2][y2] = new Piece(board[x1][y1].getType(), x2, y2, board[x1][y1].getColor(), true);
		makeBlank(board, x1, y1);
		
		return true;
	}
	
	
	
	public static boolean kingMove(Piece[][] board, int x1, int y1, int x2, int y2) {
		//castling
		if (board[x1][y1].getColor() == 'w' && x1 == 4 && y1 == 7) {
			if (x2 == 6 && y2 == 7) {
				if ( !board[4][7].isMoved() && !board[7][7].isMoved() && (board[5][7].getColor() == ' ' || board[5][7].getColor() == '#') && (board[6][7].getColor() == ' ' || board[6][7].getColor() == '#') ) {
					if (!attacked(board, 'w', 4, 7) && !attacked(board, 'w', 5, 7) && !attacked(board, 'w', 6, 7)) {
						board[6][7] = new Piece('K', 6, 7, 'w', true);
						board[5][7] = new Piece('R', 5, 7, 'w', true);
						makeBlank(board, 4, 7);
						makeBlank(board, 7, 7);
						whiteKing[0] = 6;
						whiteKing[1] = 7;
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			} else if (x2 == 2 && y2 == 7) {
				if ( !board[4][7].isMoved() && !board[0][7].isMoved() && (board[3][7].getColor() == ' ' || board[3][7].getColor() == '#') && (board[2][7].getColor() == ' ' || board[2][7].getColor() == '#') && (board[1][7].getColor() == ' ' || board[1][7].getColor() == '#') ) {
					if (!attacked(board, 'w', 4, 7) && !attacked(board, 'w', 3, 7) && !attacked(board, 'w', 2, 7)) {
						board[2][7] = new Piece('K', 2, 7, 'w', true);
						board[3][7] = new Piece('R', 3, 7, 'w', true);
						makeBlank(board, 4, 7);
						makeBlank(board, 0, 7);
						whiteKing[0] = 2;
						whiteKing[1] = 7;
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		} else if (board[x1][y1].getColor() == 'b' && x1 == 4 && y1 == 0) {
			if (x2 == 6 && y2 == 0) {
				if ( !board[4][0].isMoved() && !board[7][0].isMoved() && (board[5][0].getColor() == ' ' || board[5][0].getColor() == '#') && (board[6][0].getColor() == ' ' || board[6][0].getColor() == '#') ) {
					if (!attacked(board, 'b', 4, 0) && !attacked(board, 'b', 5, 0) && !attacked(board, 'b', 6, 0)) {
						board[6][0] = new Piece('K', 6, 0, 'b', true);
						board[5][0] = new Piece('R', 5, 0, 'b', true);
						makeBlank(board, 4, 0);
						makeBlank(board, 7, 0);
						blackKing[0] = 6;
						blackKing[1] = 0;
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			} else if (x2 == 2 && y2 == 0) {
				if ( !board[4][0].isMoved() && !board[0][0].isMoved() && (board[3][0].getColor() == ' ' || board[3][0].getColor() == '#') && (board[2][0].getColor() == ' ' || board[2][0].getColor() == '#') && (board[1][0].getColor() == ' ' || board[1][0].getColor() == '#') ) {
					if (!attacked(board, 'b', 4, 0) && !attacked(board, 'b', 3, 0) && !attacked(board, 'b', 2, 0)) {
						board[2][0] = new Piece('K', 2, 0, 'b', true);
						board[3][0] = new Piece('R', 3, 0, 'b', true);
						makeBlank(board, 4, 0);
						makeBlank(board, 0, 0);
						blackKing[0] = 2;
						blackKing[1] = 0;
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		}
		
		//regular movement
		if (x1 == x2 && y1 == y2) {
			return false;
		} else if (Math.abs(y2 - y1) > 1 || Math.abs(x2 - x1) > 1) {
			return false;
		} else if (board[x2][y2].getColor() == board[x1][y1].getColor()) {
			return false;
		}
		
		if (board[x1][y1].getColor() == 'w') {
			whiteKing[0] = x2;
			whiteKing[1] = y2;
		} else {
			blackKing[0] = x2;
			blackKing[1] = y2;
		}
		
		//move the piece and make a blank space
		board[x2][y2] = new Piece(board[x1][y1].getType(), x2, y2, board[x1][y1].getColor(), true);
		makeBlank(board, x1, y1);
		
		return true;
	}
	
	
	
	public static void boardCopy(Piece[][] original, Piece[][] copy) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				copy[i][j] = new Piece(
						original[i][j].getType(),
						original[i][j].getX(),
						original[i][j].getY(),
						original[i][j].getColor(),
						original[i][j].isMoved()
						);
			}
		}
	}
	
	
	
	public static void makeBlank(Piece[][] board, int i, int j) {
		if (j % 2 == 0) {
			if (i % 2 == 0) {
				board[i][j] = new Piece(' ', i, j, ' ', true);
			} else {
				board[i][j] = new Piece('#', i, j, '#', true);
			}
		} else {
			if (i % 2 == 0) {
				board[i][j] = new Piece('#', i, j, '#', true);
			} else {
				board[i][j] = new Piece(' ', i, j, ' ', true);
			}
		}
	}
	
}
