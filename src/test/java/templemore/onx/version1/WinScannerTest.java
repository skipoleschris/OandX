package templemore.onx.version1;

import org.testng.annotations.Test;

/**
 * @author Chris Turner
 */
@Test
public class WinScannerTest {

    public void testFindHorizontalWinOnGrid() {
        final Grid grid = Grid.build("OOO\n   \n   ");

        final WinScanner scanner = new WinScanner();
        assert(scanner.isWin(grid));
        assert(scanner.winningToken(grid).equals(Token.nought));
    }

    public void testFindVerticalWinOnGrid() {
        final Grid grid = Grid.build("O  \nO  \nO  ");

        final WinScanner scanner = new WinScanner();
        assert(scanner.isWin(grid));
        assert(scanner.winningToken(grid).equals(Token.nought));
    }

    public void testFindTopToBottomDiagonalWinOnGrid() {
        final Grid grid = Grid.build("O  \n O \n  O");

        final WinScanner scanner = new WinScanner();
        assert(scanner.isWin(grid));
        assert(scanner.winningToken(grid).equals(Token.nought));
    }

    public void testFindBottomToTopDiagonalWinOnGrid() {
        final Grid grid = Grid.build("  O\n O \nO  ");

        final WinScanner scanner = new WinScanner();
        assert(scanner.isWin(grid));
        assert(scanner.winningToken(grid).equals(Token.nought));
    }

    public void testNoWinnerOnAnEmptyGrid() {
        final Grid grid = new Grid();
        final WinScanner scanner = new WinScanner();

        assert(scanner.isWin(grid) == false);
    }

    public void testNoWinnerOnADrawnGrid() {
        final Grid grid = Grid.build("OXO\nXOX\nXOX");

        final WinScanner scanner = new WinScanner();
        assert(scanner.isWin(grid) == false);
    }

    public void testNoWinnerOnAPartialGrid() {
        final Grid grid = Grid.build("OX \nXOX\n  X");

        final WinScanner scanner = new WinScanner();
        assert(scanner.isWin(grid) == false);
    }
}
