package templemore.onx.version1;

import org.testng.annotations.Test;

/**
 * @author Chris Turner
 */
@Test
public class GridTest {

    public void testEmptyGrid() {
        final Grid grid = new Grid();

        for ( int row = 0; row < Grid.HEIGHT; row++ ) {
            for ( int col = 0; col < Grid.WIDTH; col++ ) {
                assert(grid.getToken(new Position(row, col)) == null);
            }
        }
        assert(grid.isFull() == false);
    }

    public void testAddToken() {
        final Grid grid = new Grid();

        grid.addToken(Grid.MIDDLE, Token.nought);
        assert(grid.getToken(Grid.MIDDLE).equals(Token.nought));
    }

    public void testPartFilledGridNotFull() {
        final Grid grid = new Grid();

        grid.addToken(Grid.MIDDLE, Token.nought);
        grid.addToken(Grid.TOP_LEFT, Token.nought);
        grid.addToken(Grid.BOTTOM_RIGHT, Token.nought);
        assert(grid.isFull() == false);
    }

    public void testFullGrid() {
        final Grid grid = new Grid();

        for ( int row = 0; row < Grid.HEIGHT; row++ ) {
            for ( int col = 0; col < Grid.WIDTH; col++ ) {
                grid.addToken(new Position(row, col), Token.nought);
            }
        }
        assert(grid.isFull());
    }

    @Test (expectedExceptions = IllegalStateException.class)
    public void testAddTokenAtFilledPosition() {
        final Grid grid = new Grid();

        grid.addToken(Grid.MIDDLE, Token.nought);
        grid.addToken(Grid.MIDDLE, Token.nought);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testGetTokenAtInvalidRow() {
        final Grid grid = new Grid();
        grid.getToken(new Position(3, 1));
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testGetTokenAtInvalidColumn() {
        final Grid grid = new Grid();
        grid.getToken(new Position(1, 3));
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testAddTokenAtInvalidRow() {
        final Grid grid = new Grid();
        grid.addToken(new Position(3, 1), Token.nought);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testAddTokenAtInvalidColumn() {
        final Grid grid = new Grid();
        grid.addToken(new Position(1, 3), Token.nought);
    }

    public void tesBuildingAGridFromAString() {
        final Grid grid = Grid.build("O X\nXOO\n  X");

        assert(grid.getToken(Grid.TOP_LEFT).equals(Token.nought));
        assert(grid.isPositionOccupied(Grid.TOP_MIDDLE) == false);
        assert(grid.getToken(Grid.TOP_RIGHT).equals(Token.cross));
        assert(grid.getToken(Grid.MIDDLE_LEFT).equals(Token.cross));
        assert(grid.getToken(Grid.MIDDLE).equals(Token.nought));
        assert(grid.getToken(Grid.MIDDLE_RIGHT).equals(Token.nought));
        assert(grid.isPositionOccupied(Grid.BOTTOM_LEFT) == false);
        assert(grid.isPositionOccupied(Grid.BOTTOM_MIDDLE) == false);
        assert(grid.getToken(Grid.BOTTOM_RIGHT).equals(Token.cross));
    }
}
