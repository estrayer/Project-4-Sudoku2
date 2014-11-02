public class Sudoku {

	private int[][] board;
	private boolean[][] clues; // if the element is true, then the corrisponding
								// element on the board is either one of the
								// initial
								// clues or it has a naked single, so it can't
								// be changed

	// private boolean[][][] candidatesArray;

	public static void main(String[] args) {
		// int[] testArray = new int[]{1,0,3,4,5,6,7,8,9};
		// int[][] testBoard = new
		// int[][]{testArray,testArray,testArray,testArray,testArray,testArray,testArray,testArray,testArray};
		String code = "000041000060000200000000000320600000000050040700000000000200300480000000501000000";
		int[][] testBoard = Sudoku.stringToBoard(code);
		Sudoku s = new Sudoku(testBoard);
		s.setBoard(s.board);
		s.printBoard();
		System.out.println(s.isSolved());
		int[] location = s.findBoxRepresentative(4, 2);
		System.out.println(location[0]);
		System.out.println(location[1]);

	}

	/* Default constructor */
	public Sudoku() {
		board = null;
		// candidatesArray = null;
	}

	/* creates a Sudoku with an initial board that is a copy of board */
	public Sudoku(int[][] board) {
		setBoard(board);
	}

	/**
	 * Sets the board
	 * 
	 * @param board
	 */
	public void setBoard(int[][] board) {

		this.board = board;
		// candidatesArray = createCandidatesArray();

		clues = new boolean[board.length][board.length];

		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board.length; y++) {
				if (board[x][y] != 0) {
					clues[x][y] = true;
				} else {
					clues[x][y] = false;
				}
			}
		}

	}

	/**
	 * Returns a copy of the current state of the board
	 * 
	 * @return copy of the current board
	 */
	public int[][] board() {
		int[][] copy = new int[9][9];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				copy[i][j] = board[i][j];
			}
		}
		return copy;
	}

	/**
	 * Returns the list of candidates for the specified cell.
	 * 
	 * The array contains true at index i if i is a candidate for the cell at
	 * row and column.
	 * 
	 * @param row
	 *            row of cell
	 * @param column
	 *            column of cell
	 * @return list of candidates for the specified cell
	 */
	public boolean[] candidates(int row, int column) {
		boolean[] result = new boolean[] { false, true, true, true, true, true,
				true, true, true, true };
		for (int x = 0; x < 9; x++) { // column
			int value = board[x][column];
			result[value] = false;
		}
		for (int y = 0; y < 9; y++) { // row
			int value = board[row][y];
			result[value] = false;
		}
		int[] location = findBoxRepresentative(row, column);
		for (int x = location[0]; x < location[0] + 3; x++) { // box
			for (int y = location[1]; y < location[1] + 3; y++) {
				int value = board[x][y];
				result[value] = false;
			}
		}
		return result;
	}

	/**
	 * Returns true if the current board is solved
	 * 
	 * @return whether the board is solved
	 */
	public boolean isSolved() {
		boolean solved = true;
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				if (board[x][y] == 0) {
					solved = false;
				}
			}
		}
		return solved;
	}

	/**
	 * Attempts to solve the Sudoku board.
	 * 
	 * Exits when board is solved or no updates were made to the board.
	 */
	public void solve() {
		while (!isSolved() && (nakedSingles() || hiddenSingles()))
			;
	}

	/**
	 * Takes a string of 81 digits and makes a 2D array out of it.
	 * 
	 * @param string
	 *            string of digits used to represent the board
	 * @return board 2D array
	 */
	public static int[][] stringToBoard(String string) {
		int[][] result = new int[9][9];

		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				result[x][y] = (string.charAt(9 * x + y)) - '0';
			}
		}

		return result;
	}

	/**
	 * Prints the current state of the board
	 */
	public void printBoard() {
		System.out.println("    1 2 3   4 5 6   7 8 9");
		System.out.println("  +-------+-------+-------+");
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				char letter = (char) ('A' + (3 * i + j));
				String str = String.format("%c | ", letter);
				for (int x = 0; x < 9; x++) {
					if (board[3 * i + j][x] != 0) {
						str += String.format("%d ", board[3 * i + j][x]);
					} else {
						str += "  ";
					}
					if ((x + 1) % 3 == 0) {
						str += "| ";
					}
				}
				System.out.println(str);
			}
			System.out.println("  +-------+-------+-------+");
		}
	}

	/**
	 * Attempts to solve the cell using the Naked Singles method.
	 * 
	 * @param row
	 *            row of cell
	 * @param column
	 *            column of cell
	 * @return if the board was changed
	 */
	public boolean nakedSingles() {

		boolean changed = false;

		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board.length; col++) {
				if(board[row][col] == 0){
				
				boolean[] candidatesArray = candidates(row, col);
				int candidateCount = 0;
				int trueCandidate = 0;
				for (int i = 0; i < candidatesArray.length; i++) {
					if (candidatesArray[i]) {
						candidateCount++;
						trueCandidate = i;
					}
				}
				if (candidateCount == 1) {
					board[row][col] = trueCandidate;
					changed = true;
				}
				}
			}
		}

		return changed;
	}

	public boolean hiddenSinglesCell(int row, int column) {
		boolean changed = false;
		boolean[] candidatesArray = candidates(row, column);
		for (int candidate = 1; candidate <= 9; candidate++) {
			if (!candidatesArray[candidate]) {
				continue;
			}

			boolean candidateExists = false;

			for (int x = 0; x < 9; x++) { // column
				if (candidates(x, column)[candidate]) {
					candidateExists = true;
				}
			}

			for (int y = 0; y < 9; y++) { // row
				if (candidates(row, y)[candidate]) {
					candidateExists = true;
				}
			}

			int[] location = findBoxRepresentative(row, column);
			for (int x = location[0]; x < location[0] + 3; x++) {
				for (int y = location[1]; y < location[1] + 3; y++) {
					if (candidates(row, y)[candidate]) {
						candidateExists = true;
					}
				}
			}
			
			if (!candidateExists) {
				board[row][column] = candidate;
				changed = true;
				break;
			}
		}
		return changed;
	}

	/**
	 * Attempts to solve the cell using the Hidden Singles method.
	 * 
	 * @param row
	 *            row of cell
	 * @param column
	 *            column of cell
	 * @return if the board was changed
	 */
	public boolean hiddenSingles() {
		// TODO
		boolean changed = false;
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board.length; col++) {
				changed = hiddenSinglesCell(row, col);
			}
		}

		return changed;
	}

	/**
	 * Finds which box a cell belongs in.
	 * 
	 * @param row
	 *            row of the cell
	 * @param column
	 *            column of the cell
	 * @return the number of the box the cell is found in
	 */
	private int findBox(int row, int column) {
		int boxRow = row / 3;
		int boxColumn = column / 3;
		return 3 * boxColumn + boxRow + 1;
	}

	/**
	 * Finds the first cell in a given box
	 * 
	 * @param box
	 *            box number
	 * @return the coordinates in an array in format {x, y}
	 */
	private int[] findBoxRepresentative(int row, int column) {
		int boxRow = row / 3;
		int boxColumn = column / 3;
		int x = 3 * boxRow;
		int y = 3 * boxColumn;
		return new int[] { x, y };
	}

	// private boolean[][][] createCandidatesArray() {
	// boolean[][][] result = new boolean[9][9][10];
	// for (int x = 0; x < board.length; x++) {
	// for (int y = 0; y < board[x].length; y++) {
	// result[x][y] = candidates(x, y);
	// }
	// }
	// return result;
	// }

}
