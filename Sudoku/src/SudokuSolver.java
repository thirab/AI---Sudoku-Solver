import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SudokuSolver {

	private int count = 0;
	// Global vars the puzzle, file loaded, total nodes used, and backtracks
	// that have occured
	private bigGrid puzzle;
	private File theInitialPuzzle;
	private int backtrackCount = 0;

	/**
	 * Create the SudokuSolver which creates a 3x3 big grid made up of 3x3 small
	 * grids.
	 */
	public SudokuSolver() {
		puzzle = new bigGrid();

	}

	/**
	 * setPuzzle reads in a file containing 9x9 numbers seperated by spaces.
	 * Invalid files will throw errors, including ones not containing numbers.
	 * 
	 * @param f
	 *            the file to be loaded
	 * @throws IOException
	 *             if a file is invalid
	 */
	public void setPuzzle(File f) throws IOException {
		theInitialPuzzle = f;
		BufferedReader br = null;

		br = new BufferedReader(new FileReader(theInitialPuzzle));
		for (int i = 0; i < 9; i++) {
			String currentLine = br.readLine();
			String[] lineArray = currentLine.split(" ");
			for (int j = 0; j < 9; j++) {
				puzzle.setCellValue(i, j, Integer.parseInt(lineArray[j]));
			}
		}
		br.close();

	}

	public boolean unset(int x, int y) {
		if (puzzle.getCellValue(x, y) == 0) {
			return true;
		}
		return false;
	}

	public boolean mostBasic(int row, int col) {
		int x = row;
		int y = col;
		if (puzzleSolved()) {
			return true;
		} else if (!unset(x, y)) {
			if (x < 8) {
				if (solveBasic(x + 1, y)) {
					return true;
				}
			} else if (x == 8 && y == 8) {
				solveBasic(0, 0);
			} else {
				if (solveBasic(0, y + 1)) {
					return true;
				}
			}
		} else{
			for (int i = 0; i < 9; i++) {
				count++;
				puzzle.setCellValue(x, y, i);
				if(puzzle.validAcross(y,i) && puzzle.validSmallGrid(x,y,i) && puzzle.validUpDown(x,i)){
					return true;
				}
				if (nextBasic(x, y)) {
					return true;
				} else {
					backtrackCount++;
					puzzle.setCellValue(x, y, 0);
				}
			}
		}
		return false;
	}

	/**
	 * Forward looking, basic
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean solveBasic(int row, int col) {
		// TODO solve the puzzle with forward checking
		int x = row;
		int y = col;
		if (puzzleSolved()) {
			return true;
		} else if (!unset(x, y)) {
			if (x < 8) {
				if (solveBasic(x + 1, y)) {
					return true;
				}
			} else if (x == 8 && y == 8) {
				solveBasic(0, 0);
			} else {
				if (solveBasic(0, y + 1)) {
					return true;
				}
			}
		} else {
			List l = puzzle.validOptions(x, y);
			for (int a = 0; a < l.size(); a++) {
				count++;
				int testVal = (Integer) l.get(a);

				puzzle.setCellValue(x, y, testVal);
				if (next(x, y)) {
					return true;
				} else {
					backtrackCount++;
					puzzle.setCellValue(x, y, 0);
				}
			}
		}
		return false;
	}
	public boolean nextBasic(int x, int y) {
		if (x < 8) {
			return mostBasic(x + 1, y);
		} else if (x == 8 && y == 8) {
			return mostBasic(0, 0);
		}
		return mostBasic(0, y + 1);
	}

	public boolean next(int x, int y) {
		if (x < 8) {
			return solveBasic(x + 1, y);
		} else if (x == 8 && y == 8) {
			return solveBasic(0, 0);
		}
		return solveBasic(0, y + 1);
	}

	public boolean solveForwardMRV() {
		// forward with MRV ( minimum remaining value )
		if (puzzleSolved()) {
			return true;
		}
		int leastOptions = 8;
		int x = 0;
		int y = 0;
		List l = new ArrayList();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (puzzle.getCellValue(i, j) == 0) {
					List holder = puzzle.validOptions(i, j);
					if (holder.size() < leastOptions) {
						x = i;
						y = j;
						leastOptions = holder.size();
						l = holder;
					}
				}
			}
		}
		if (!l.isEmpty()) {
			for (int a = 0; a < leastOptions; a++) {
				puzzle.setCellValue(x, y, (Integer) l.get(a));
				count++;
				if (solveForwardMRV()) {
					return true;
				} else {
					backtrackCount++;
					puzzle.setCellValue(x, y, 0);
				}
			}

		}
		return false;

	}

	public boolean solveForwardMRVLVC() {
		// Extra credit: Backtracking with forward checking, the MRV heuristic,
		// and the
		// least constrained value heuristic (LCV).
		if (puzzleSolved()) {
			return true;
		}
		int leastOptions = 8;
		ArrayList<Point> points = new ArrayList<Point>();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (unset(i, j)) {
					List holder = puzzle.validOptions(i, j);
					if (holder.size() < leastOptions) {
						points.clear();
						leastOptions = holder.size();
						points.add(new Point(i, j));
					} else if (holder.size() == leastOptions) {
						points.add(new Point(i, j));
					}
				}
			}
		}
		if (!points.isEmpty()) {
			// TODO select the point in points that has the least constrained
			// value LCV
			Point p = points.get(0);
			for (int a = 0; a < points.size(); a++) {
				count++;
				List l = puzzle.validOptions((int) p.getX(), (int) p.getY());
				puzzle.setCellValue((int) p.getX(), (int) p.getY(),
						(Integer) l.get(a));
				if (solveForwardMRV()) {
					return true;
				} else {
					backtrackCount++;
				}
			}

		}
		return false;
	}

	/**
	 * puzzleSolved checks if the lines are valid and if the small grids are
	 * valid.
	 * 
	 * @return if the bigGrid is valid
	 */
	public boolean puzzleSolved() {
		boolean solved = true;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (!puzzle.getSmallGrid(i, j).valid()) {
					solved = false;
				}
				if (!puzzle.validLines()) {
					solved = false;
				}
			}
		}
		return solved;
	}

	public void printPuzzle() {
		System.out.println("----The current puzzle ----");
		for (int i = 0; i < 9; i++) {
			String line = "";
			for (int j = 0; j < 9; j++) {
				line = line + puzzle.getCellValue(i, j) + " ";
			}
			System.out.println(line);
		}
	}

	/**
	 * Puzzle Begins Here, a file is fed in through command line args. The original puzzle is printed out, then the solved puzzle with the time it took, nodes accessed, and backtracks taken.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// create the sudokuSolver instance
		SudokuSolver solve = new SudokuSolver();
		long startTime = System.currentTimeMillis();

		if (args.length < 4) {
			// TODO uncomment for command line use
			// File f = new File(args[0]);
			File f = new File("easyPuzzle.txt");
			boolean solved = false;
			try {
				solve.setPuzzle(f);
				System.out.println("You have loaded in the following puzzle:");
				solve.printPuzzle();
			} catch (IOException e) {
				System.out.println("Invalid file");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// TODO remove <=1 set to ==1 , this is for testing
			if (args.length <= 1) {
				// TODO native backtracking
				solved = solve.solveBasic(0, 0);
				//solved = solve.mostBasic(0, 0);

			} else if ((args[1].equals("MRV") || args[1].equals("FC"))
					&& (args[2].equals("FC") || args[2].equals("MRV"))
					&& (!args[1].equals(args[2]))) {
				// TODO with MRV and FC
				solved = solve.solveForwardMRV();
			} else if (args[1].equals("LCV")) {
				solved = solve.solveForwardMRVLVC();
			} else {
				System.out
						.println("You have entered an invalid command! \n you may use no command, MRV FC, FC MRV, or LCV after the filename");
				System.out
						.println("Please use the format Java SudokuSolver fileName commandType");
				System.out
						.println("The program will now terminate please load again with correct format.");
				return;
			}
			if (solved) {
				long endTime = System.currentTimeMillis();
				// print out the solved puzzle
				System.out.println("The puzzle has been solved: ");
				solve.printPuzzle();
				System.out
						.println("The puzzle took the following time to solve (in millis): "
								+ (endTime - startTime));
				System.out.println("The puzzle was solved after visiting "
						+ solve.count + " nodes");
				System.out.println("The puzzle needed to backtrack  "
						+ solve.backtrackCount + " times to find a solution");
			} else {
				System.out
						.println("I'm sorry but this puzzle could not be solved, it is invalid");
				System.out.println("Please load a new puzzle and try again");
				return;
			}

		} else {

			System.out.println("You have entered an invalid command");
			System.out
					.println("Please use the format Java SudokuSolver fileName commandType");
			System.out
					.println("The program will now terminate please load again with correct format.");
			return;

		}

	}
}
