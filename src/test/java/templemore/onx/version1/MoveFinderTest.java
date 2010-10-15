package templemore.onx.version1;

import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Chris Turner
 */
@Test
public class MoveFinderTest {

    // Tests to find that we can win if possible

    public void testWinningHorizontalPositionFinder() {
        final Grid grid = Grid.build("OO \n   \n   ");

        final MoveFinder finder = new MoveFinder();
        assertThat(finder.findBestPosition(grid, Token.nought), equalTo(new Position(0, 2)));
    }

    public void testWinningVerticalPositionFinder() {
        final Grid grid = Grid.build("O  \nO  \n   ");

        final MoveFinder finder = new MoveFinder();
        assertThat(finder.findBestPosition(grid, Token.nought), equalTo(new Position(2, 0)));
    }

    public void testWinningDiagonalPositionFinder() {
        final Grid grid = Grid.build("   \n O \nO  ");

        final MoveFinder finder = new MoveFinder();
        assertThat(finder.findBestPosition(grid, Token.nought), equalTo(new Position(0, 2)));
    }

    // Tests to find that if we can't win then we can at least block the opponent from winning

    public void testBlockingHorizontalPositionFinder() {
        final Grid grid = Grid.build("OO \n   \n   ");

        final MoveFinder finder = new MoveFinder();
        assertThat(finder.findBestPosition(grid, Token.cross), equalTo(new Position(0, 2)));
    }

    public void testBlockingVerticalPositionFinder() {
        final Grid grid = Grid.build("O  \nO  \n   ");

        final MoveFinder finder = new MoveFinder();
        assertThat(finder.findBestPosition(grid, Token.cross), equalTo(new Position(2, 0)));
    }

    public void testBlockingDiagonalPositionFinder() {
        final Grid grid = Grid.build("   \n O \nO  ");

        final MoveFinder finder = new MoveFinder();
        assertThat(finder.findBestPosition(grid, Token.cross), equalTo(new Position(0, 2)));
    }

    // Tests to find that if we can't win an block we can find a position that gives us a
    // certain win on the next go

    public void testDoubleWinPositionFinder() {
        final Grid grid = Grid.build("OX \n O \n  X");

        final MoveFinder finder = new MoveFinder();
        assertThat(finder.findBestPosition(grid, Token.nought), equalTo(new Position(1, 0)));
    }

    // Tests to find that if we can't find a double win spot that we can block our opponent
    // from finding a double win spot

    public void testDoubleBlockPositionFinder() {
        final Grid grid = Grid.build("OX \n O \n  X");

        final MoveFinder finder = new MoveFinder();
        assertThat(finder.findBestPosition(grid, Token.cross), equalTo(new Position(1, 0)));
    }

    // Tests to find that when there are no options that we pick the best position

    public void testMiddlePositionSelectedIfOnDoubleEmptyLine() {
        final Grid grid = Grid.build("O  \n   \n X ");

        final MoveFinder finder = new MoveFinder();
        assertThat(finder.findBestPosition(grid, Token.nought), equalTo(new Position(1, 1)));
    }

    public void testMostSuitablePositionSelectedIfOnDoubleEmptyLine() {
        final Grid grid = Grid.build("O  \n X \n   ");

        final MoveFinder finder = new MoveFinder();
        assertThat(finder.findBestPosition(grid, Token.nought), equalTo(new Position(0, 2)));
    }

    public void testMiddlePositionSelectedIfOnEmptyLine() {
        final Grid grid = Grid.build("   \n   \n   ");

        final MoveFinder finder = new MoveFinder();
        assertThat(finder.findBestPosition(grid, Token.nought), equalTo(new Position(1, 1)));
    }

    public void testMostSuitablePositionSelectedIfOnEmptyLine() {
        final Grid grid = Grid.build("   \n X \n   ");

        final MoveFinder finder = new MoveFinder();
        assertThat(finder.findBestPosition(grid, Token.nought), equalTo(new Position(0, 0)));
    }

    public void testPickMiddlePositionIfFree() {
        final Grid grid = Grid.build("OOX\nX O\nXXO");

        final MoveFinder finder = new MoveFinder();
        assertThat(finder.findBestPosition(grid, Token.nought), equalTo(new Position(1, 1)));
    }
}
