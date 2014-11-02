import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.Arrays;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class SudokuTest {

	@Test
	public void testSetBoard() {
		Sudoku game = new Sudoku();
		game.setBoard(Sudoku.
				stringToBoard("123564897456978031089231564645"
						+ "123978897056312231789645564897123078312456312645789"));
		int[][] board = Sudoku.
				stringToBoard("12356489745697803108923156464"
						+ "5123978897056312231789645564897123078312456312645789");
		assertArrayEquals(game.board(), board);
	}

	@Test
	public void testBoard() {
		Sudoku game = new Sudoku();
		int[][] board = Sudoku.
				stringToBoard("12356489745697803108923156464512397889705631"
						+ "2231789645564897123078312456312645789");
		game.setBoard(board);

		assertArrayEquals(game.board(), board);
	}

	@Test
	public void testCandidates() {
		Sudoku game = new Sudoku();
		game.setBoard(Sudoku.
				stringToBoard("4127365890000001065680103700008502101"
						+ "00000008087090000030070865800000000000908401"));

		boolean[] expected = { false, false, false, true, false, false, false,
				true, false, true };
		boolean[] actual = game.candidates(1, 0);
		assertTrue(Arrays.equals(expected, actual));
	}

	@Test
	public void testIsSolved() {
		Sudoku game = new Sudoku();
		game.setBoard(Sudoku.
				stringToBoard("53467891267219534819834256785976142342685379"
						+ "1713924856961537284287419635345286179"));
		assertTrue(game.isSolved());
	}

	@Test
	public void testIsSolvedFalse() {
		Sudoku game = new Sudoku();
		game.setBoard(Sudoku.
				stringToBoard("53467891267219534819830256785976142"
						+ "3426853791713924856961537284287419635345286179"));
		assertFalse(game.isSolved());
	}

	@Test
	public void testSolve() {
		Sudoku game = new Sudoku(
				Sudoku.stringToBoard("50402900300000009580000572060"
						+ "7051300240000058009480206068500001410000000700810902"));
		game.solve();
		assertTrue(game.isSolved());
	}

	@Test
	public void testStringToBoard() {
		Sudoku game = new Sudoku();
		game.setBoard(Sudoku.
				stringToBoard("412736589000000106568010370000850210"
						+ "100000008087090000030070865800000000000908401"));
		int[][] expected = new int[][] { { 4, 1, 2, 7, 3, 6, 5, 8, 9 },
				{ 0, 0, 0, 0, 0, 0, 1, 0, 6 }, { 5, 6, 8, 0, 1, 0, 3, 7, 0 },
				{ 0, 0, 0, 8, 5, 0, 2, 1, 0 }, { 1, 0, 0, 0, 0, 0, 0, 0, 8 },
				{ 0, 8, 7, 0, 9, 0, 0, 0, 0 }, { 0, 3, 0, 0, 7, 0, 8, 6, 5 },
				{ 8, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 9, 0, 8, 4, 0, 1 } };
		boolean allEqual = true;
		for (int i = 0; i < 9; i++) {
			if (!Arrays.equals(expected[i], game.board()[i])) {
				allEqual = false;
			}
		}
		assertTrue(allEqual);
	}

	@Test
	public void testStringToBoardFalse() {
		Sudoku game = new Sudoku();
		game.setBoard(Sudoku.
				stringToBoard("01273658900000010656801037000085021"
						+ "0100000008087090000030070865800000000000908401"));
		int[][] expected = new int[][] { { 4, 1, 2, 7, 3, 6, 5, 8, 9 },
				{ 0, 0, 0, 0, 0, 0, 1, 0, 6 }, { 5, 6, 8, 0, 1, 0, 3, 7, 0 },
				{ 0, 0, 0, 8, 5, 0, 2, 1, 0 }, { 1, 0, 0, 0, 0, 0, 0, 0, 8 },
				{ 0, 8, 7, 0, 9, 0, 0, 0, 0 }, { 0, 3, 0, 0, 7, 0, 8, 6, 5 },
				{ 8, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 9, 0, 8, 4, 0, 1 } };

		boolean allEqual = true;
		for (int i = 0; i < 9; i++) {
			if (!Arrays.equals(expected[i], game.board()[i])) {
				allEqual = false;
			}
		}
		assertFalse(allEqual);
	}

	@Test
	public void testPrintBoard() {
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		Sudoku game = new Sudoku(
				Sudoku.stringToBoard("5040290030000000958000"
						+ "05720607051300240000058009480206068500001410000000700810902"));
		game.printBoard();
		String expected = "    1 2 3   4 5 6   7 8 9\r\n"
				+ "  +-------+-------+-------+\r\n"
				+ "A | 5   4 |   2 9 |     3 | \r\n"
				+ "B |       |       |   9 5 | \r\n"
				+ "C | 8     |     5 | 7 2   | \r\n"
				+ "  +-------+-------+-------+\r\n"
				+ "D | 6   7 |   5 1 | 3     | \r\n"
				+ "E | 2 4   |       |   5 8 | \r\n"
				+ "F |     9 | 4 8   | 2   6 | \r\n"
				+ "  +-------+-------+-------+\r\n"
				+ "G |   6 8 | 5     |     1 | \r\n"
				+ "H | 4 1   |       |       | \r\n"
				+ "I | 7     | 8 1   | 9   2 | \r\n"
				+ "  +-------+-------+-------+\r\n";

		assertEquals(expected, outContent.toString());
	}

	@Test
	public void testHiddenSingles() {
		Sudoku game = new Sudoku();
		int[][] board = Sudoku.
				stringToBoard("02800700001608307000002085113729000000073000000004630729007000"
						+ "0000860140000300700");

		game.setBoard(board);

		game.hiddenSingles();

		board = Sudoku.
				stringToBoard("028007000016083070000620851137290000000730000000"
						+ "046307290070000000860140000300700");

		assertArrayEquals(board, game.board());

	}

	@Test
	public void testNakedSingles() {
		Sudoku game = new Sudoku();

		int[][] board = Sudoku.
				stringToBoard("4127365890000001065680103700008502101000000"
						+ "08087090000030070865800000000000908401");

		game.setBoard(board);

		game.nakedSingles();

		board = Sudoku.
				stringToBoard("41273658900000010656801037000085021010000000808709"
						+ "0600030070865800000000000908401");

		assertArrayEquals(game.board(), board);
	}

}
