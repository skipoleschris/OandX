package templemore.onx.version1;

import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Chris Turner
 */
@Test
public class GridTest {

    public void testEmptyGrid() {
        final Grid grid = new Grid();

        for ( int row = 0; row < Grid.HEIGHT; row++ ) {
            for ( int col = 0; col < Grid.WIDTH; col++ ) {
                assertThat(grid.getToken(new Position(row, col)), nullValue());
            }
        }
        assertThat(grid.isFull(), equalTo(false));
    }

    public void testAddToken() {
        final Grid grid = new Grid();

        grid.addToken(Grid.MIDDLE, Token.nought);
        assertThat(grid.getToken(Grid.MIDDLE), equalTo(Token.nought));
    }

    public void testPartFilledGridNotFull() {
        final Grid grid = new Grid();

        grid.addToken(Grid.MIDDLE, Token.nought);
        grid.addToken(Grid.TOP_LEFT, Token.nought);
        grid.addToken(Grid.BOTTOM_RIGHT, Token.nought);
        assertThat(grid.isFull(), equalTo(false));
    }

    public void testFullGrid() {
        final Grid grid = new Grid();

        for ( int row = 0; row < Grid.HEIGHT; row++ ) {
            for ( int col = 0; col < Grid.WIDTH; col++ ) {
                grid.addToken(new Position(row, col), Token.nought);
            }
        }
        assertThat(grid.isFull(), equalTo(true));
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

        assertThat(grid.getToken(Grid.TOP_LEFT), equalTo(Token.nought));
        assertThat(grid.isPositionOccupied(Grid.TOP_MIDDLE), equalTo(false));
        assertThat(grid.getToken(Grid.TOP_RIGHT), equalTo(Token.cross));
        assertThat(grid.getToken(Grid.MIDDLE_LEFT), equalTo(Token.cross));
        assertThat(grid.getToken(Grid.MIDDLE), equalTo(Token.nought));
        assertThat(grid.getToken(Grid.MIDDLE_RIGHT), equalTo(Token.nought));
        assertThat(grid.isPositionOccupied(Grid.BOTTOM_LEFT), equalTo(false));
        assertThat(grid.isPositionOccupied(Grid.BOTTOM_MIDDLE), equalTo(false));
        assertThat(grid.getToken(Grid.BOTTOM_RIGHT), equalTo(Token.cross));
    }
}
