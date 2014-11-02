
import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.Arrays;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;


public class SudokuTest {

	@Test
	public void testSetBoard() {
		Sudoku game = new Sudoku();
		game.setBoard(Sudoku.stringToBoard("123564897456978031089231564645123978897056312231789645564897123078312456312645789"));
		int[][] board = Sudoku.stringToBoard("123564897456978031089231564645123978897056312231789645564897123078312456312645789");
		assertArrayEquals(game.board(), board);
	}

	@Test
	public void testBoard() {
		Sudoku game = new Sudoku();
		int[][] board = Sudoku.stringToBoard("123564897456978031089231564645123978897056312231789645564897123078312456312645789");
		game.setBoard(board);
		
		assertArrayEquals(game.board(), board);
	}

	@Test
	public void testCandidates() {
		Sudoku game = new Sudoku();
		game.setBoard(Sudoku.stringToBoard("412736589000000106568010370000850210100000008087090000030070865800000000000908401"));
		
		boolean[] expected = {false, false, false, true, false, false, false, true, false, true};
		boolean[] actual = game.candidates(1, 0);
		assertTrue(Arrays.equals(expected, actual));
	}

	@Test
	public void testIsSolved() {
		Sudoku game = new Sudoku();
		game.setBoard(Sudoku.stringToBoard("534678912672195348198342567859761423426853791713924856961537284287419635345286179"));
		assertTrue(game.isSolved());
	}
	
	@Test
	public void testIsSolvedFalse() {
		Sudoku game = new Sudoku();
		game.setBoard(Sudoku.stringToBoard("534678912672195348198302567859761423426853791713924856961537284287419635345286179"));
		assertFalse(game.isSolved());
	}

	@Test
	public void testSolve() {
		fail("Not yet implemented");
	}

	@Test
	public void testStringToBoard() {
		fail("Not yet implemented");
	}

	@Test
	public void testPrintBoard() {
		fail("Not yet implemented");
	}

	@Test
	public void testHiddenSingles() {
		fail("Not yet implemented");
	}
	
	public void testNakedSingles() {
		
	}

}
